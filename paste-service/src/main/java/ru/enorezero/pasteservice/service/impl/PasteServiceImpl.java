package ru.enorezero.pasteservice.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.enorezero.pasteservice.exception.NoAccessForPasteException;
import ru.enorezero.pasteservice.exception.FailedToDecryptLinkException;
import ru.enorezero.pasteservice.model.PasteEntity;
import ru.enorezero.pasteservice.model.Visibility;
import ru.enorezero.pasteservice.model.dto.PasteRequest;
import ru.enorezero.pasteservice.model.dto.PasteResponse;
import ru.enorezero.pasteservice.repository.PasteRepository;
import ru.enorezero.pasteservice.service.PasteService;
import ru.enorezero.pasteservice.service.StorageService;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PasteServiceImpl implements PasteService {

    private static final ArrayDeque<PasteEntity> publicPastes = new ArrayDeque<>();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StorageService storage;

    @Autowired
    PasteRepository pasteRepository;

    @Value("${cloud.aws.s3.credentials.bucket.name}")
    private String bucketName;

    @Override
    @Transactional
    @Cacheable(
            value = "PasteService::getByHash",
            key = "#hash"
    )
    public PasteResponse getByHash(String hash, String username) {
        Optional<PasteEntity> foundPaste = Optional.of(pasteRepository.findOptionalByPastesId(decodeByBase64(hash))
                .orElseThrow(() -> new NotFoundException("Паста не найдена")));

        PasteEntity paste =  foundPaste.get();

        if(LocalDateTime.now().isAfter(paste.getExpirationTime())){
            pasteRepository.delete(paste);

            throw new NotFoundException("Время жизни пасты истекло");
        }

        if(paste.getStatus().equals( Visibility.PRIVATE) && !paste.getUsername().equals(username)){
            throw new NoAccessForPasteException("Вы не обладаете нужными правами для получения этой пасты");
        }

        String data = storage.download(bucketName, paste.getKey());

        return new PasteResponse(data, paste.getStatus());
    }

    @Override
    @Transactional
    @CacheEvict(
            value = "PasteService::getByHash",
            key = "#hash"
    )
    public void deleteByHash(String hash, String username) {
        Optional<PasteEntity> foundPaste = Optional.of(pasteRepository.findOptionalByPastesId(decodeByBase64(hash))
                .orElseThrow(() -> new NotFoundException("Данной пасты не существует")));

        PasteEntity paste =  foundPaste.get();

        if(!username.equals(paste.getUsername())){
            throw new NoAccessForPasteException("Вы не обладаете нужными правами для получения этой пасты");
        }

        if(paste.getStatus().equals(Visibility.PUBLIC)){
            publicPastes.remove(paste);
        }

        pasteRepository.delete(paste);
    }

    @Override
    public List<PasteResponse> getPublicPastes() {
        return publicPastes.stream().map(o -> modelMapper.map(o, PasteResponse.class)).toList();
    }

    @Override
    @Transactional
    public String create(PasteRequest request, String username) {
        if(request.getStatus().equals(Visibility.PRIVATE) && username==null){
            throw new NoAccessForPasteException("Создавать приватные пасты могут только зарегистирированные пользователи");
        }

        String key = storage.upload("rubin",request.getData());
        PasteEntity paste = PasteEntity.builder()
                .key(key)
                .creationTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusSeconds(request.getLifetime().getSeconds()))
                .status(request.getStatus())
                .username(username)
                .build();

        pasteRepository.save(paste);

        if(publicPastes.size() == 10){
            publicPastes.pop();
        }

        if(request.getStatus().equals(Visibility.PUBLIC)) {
            publicPastes.push(paste);
        }

        return encodeByBase64(paste.getPastesId());
    }

    @Override
    public List<PasteResponse> getUserPastes(String username) {
        List<PasteEntity> foundPastes = pasteRepository.findPasteEntitiesByUsername(username);

        return foundPastes.stream().map(o -> modelMapper.map(o, PasteResponse.class)).toList();
    }

    private String encodeByBase64(Long id) {
        return Base64.getEncoder().encodeToString(Long.toString(id).getBytes()) ;
    }

    private Long decodeByBase64(String hash) {
        try {
            return Long.parseLong(new String(Base64.getDecoder().decode(hash.getBytes())));
        }catch (NumberFormatException e){
            throw new FailedToDecryptLinkException("Невозможно расхешировать строку");
        }
    }

}
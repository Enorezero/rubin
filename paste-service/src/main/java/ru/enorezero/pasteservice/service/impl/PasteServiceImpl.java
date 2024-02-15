package ru.enorezero.pasteservice.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.enorezero.pasteservice.exception.NoAccessForPrivatePasteException;
import ru.enorezero.pasteservice.exception.UnhashableLinkException;
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

    private static ArrayDeque<PasteResponse> publicResponses = new ArrayDeque<>();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StorageService storage;

    @Autowired
    PasteRepository pasteRepository;

    @Value("${cloud.aws.s3.credentials.bucket.name}")
    private String bucketName;

    @Transactional
    @Override
    public PasteResponse getByHash(String hash, String username) {
        Optional<PasteEntity> foundEntity = Optional.of(pasteRepository.findOptionalByPastesId(decodeByBase64(hash))
                .orElseThrow(() -> new NotFoundException("Паста не найдена")));

        PasteEntity paste =  foundEntity.get();

        if(LocalDateTime.now().isAfter(paste.getExpirationTime())){
            pasteRepository.delete(paste);

            throw new NotFoundException("Время жизни пасты истекло");
        }

        if(!paste.getStatus().equals( Visibility.PRIVATE) || !paste.getUsername().equals(username)){
            throw new NoAccessForPrivatePasteException("Вы не обладаете правами для получения этой приватной пасты");
        }

        String data = storage.download(bucketName, paste.getKey());

        return new PasteResponse(data, paste.getStatus());
    }

    @Override
    public List<PasteResponse> getPublicPastes() {
        return publicResponses.stream().toList();
    }

    @Transactional
    @Override
    public String create(PasteRequest request, String username) {
        String key = storage.upload("rubin",request.getData());

        PasteEntity paste = new PasteEntity().builder()
                .key(key)
                .creationTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusSeconds(request.getLifetime().getSeconds()))
                .status(request.getStatus())
                .username(username)
                .build();

        pasteRepository.save(paste);

        if(request.getStatus().equals(Visibility.PUBLIC)) {
            PasteResponse response = modelMapper.map(request, PasteResponse.class);
            publicResponses.push(response);
        }

        return encodeByBase64(paste.getPastesId());
    }

    @Override
    public List<PasteResponse> getUserPastes(String username) {
        List<PasteEntity> foundPastes = pasteRepository.findPasteEntitiesByUsername(username);
        List<PasteResponse> responses = new ArrayList<>();

        for(PasteEntity paste : foundPastes){
            responses.add(modelMapper.map(paste, PasteResponse.class));
        }

        return responses;
    }

    private String encodeByBase64(Long id) {
        return Base64.getEncoder().encodeToString(Long.toString(id).getBytes()) ;
    }

    private Long decodeByBase64(String hash) {
        try {
            return Long.parseLong(new String(Base64.getDecoder().decode(hash.getBytes())));
        }catch (NumberFormatException e){
            throw new UnhashableLinkException("Невозможно расхешировать строку");
        }
    }

}


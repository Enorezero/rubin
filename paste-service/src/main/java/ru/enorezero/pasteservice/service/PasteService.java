package ru.enorezero.pasteservice.service;

import ru.enorezero.pasteservice.model.dto.PasteRequest;
import ru.enorezero.pasteservice.model.dto.PasteResponse;

import java.util.List;

public interface PasteService {
    PasteResponse getByHash(String hash, String username);
    void deleteByHash(String hash, String username);
    List<PasteResponse> getPublicPastes();
    String create(PasteRequest request, String username);
    List<PasteResponse> getUserPastes(String username);


}
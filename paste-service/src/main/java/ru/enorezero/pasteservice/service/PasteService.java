package ru.enorezero.pasteservice.service;

import ru.enorezero.pasteservice.model.dto.PasteRequest;
import ru.enorezero.pasteservice.model.dto.PasteResponse;

import java.util.List;

public interface PasteService {
    public PasteResponse getByHash(String hash, String username);
    public List<PasteResponse> getPublicPastes();
    public String create(PasteRequest request, String username);
    public List<PasteResponse> getUserPastes(String username);

}

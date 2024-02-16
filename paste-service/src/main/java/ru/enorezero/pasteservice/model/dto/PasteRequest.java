package ru.enorezero.pasteservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.enorezero.pasteservice.model.ExpirationTime;
import ru.enorezero.pasteservice.model.Visibility;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteRequest {
    private String data;
    private ExpirationTime lifetime;
    private Visibility status;
}
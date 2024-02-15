package ru.enorezero.pasteservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.enorezero.pasteservice.model.Visibility;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteResponse {
    private String data;
    private Visibility status;
}

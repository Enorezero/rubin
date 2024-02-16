package ru.enorezero.pasteservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.enorezero.pasteservice.model.Visibility;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteResponse implements Serializable {
    private String data;
    private Visibility status;
}
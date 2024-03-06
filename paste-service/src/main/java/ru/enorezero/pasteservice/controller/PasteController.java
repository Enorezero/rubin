package ru.enorezero.pasteservice.controller;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.enorezero.pasteservice.model.dto.PasteRequest;
import ru.enorezero.pasteservice.model.dto.PasteResponse;
import ru.enorezero.pasteservice.service.PasteService;

import java.util.List;

@RestController
@Data
@RequestMapping("/paste")
public class PasteController {

    private final String USERNAME_HEADER = "username";

    @Autowired
    PasteService pasteService;

    @GetMapping("/{hash}")
    public ResponseEntity<PasteResponse> getByHash(@PathVariable String hash, @Nullable @RequestHeader(USERNAME_HEADER) String username){
        return ResponseEntity.status(HttpStatus.OK).body(pasteService.getByHash(hash, username));
    }

    @DeleteMapping("/{hash}")
    public ResponseEntity<?> deleteByHash(@PathVariable String hash, @Nullable @RequestHeader(USERNAME_HEADER) String username){
        pasteService.deleteByHash(hash, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Паста удалена");
    }

    @GetMapping("/")
    public ResponseEntity<List<PasteResponse>> getLastPublic(){
        return ResponseEntity.status(HttpStatus.OK).body(pasteService.getPublicPastes());
    }

    @PostMapping("/")
    public ResponseEntity<String> createPaste(@RequestBody PasteRequest request, @Nullable @RequestHeader(USERNAME_HEADER) String username){
        return ResponseEntity.status(HttpStatus.CREATED).body(pasteService.create(request, username));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PasteResponse>> getUserPastes(@NonNull @RequestHeader(USERNAME_HEADER) String username){
        return ResponseEntity.status(HttpStatus.OK).body(pasteService.getUserPastes(username));
    }


}
package ru.enorezero.pasteservice.controller;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PasteService pasteService;

    @GetMapping("/{hash}")
    public ResponseEntity<PasteResponse> getByHash(@PathVariable String hash, @Nullable @RequestHeader("username") String username){
        return ResponseEntity.ok(pasteService.getByHash(hash, username));
    }

    @DeleteMapping("/{hash}")
    public ResponseEntity<?> deleteByHash(@PathVariable String hash, @Nullable @RequestHeader("username") String username){
        pasteService.deleteByHash(hash, username);
        return ResponseEntity.ok("Паста удалена");
    }

    @GetMapping("/")
    public ResponseEntity<List<PasteResponse>> getLastPublic(){
        return ResponseEntity.ok(pasteService.getPublicPastes());
    }

    @PostMapping("/")
    public ResponseEntity<String> createPaste(@RequestBody PasteRequest request, @Nullable @RequestHeader("username") String username){
        return ResponseEntity.ok(pasteService.create(request, username));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PasteResponse>> getUserPastes(@NonNull @RequestHeader("username") String username){
        return ResponseEntity.ok(pasteService.getUserPastes(username));
    }


}
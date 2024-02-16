package ru.enorezero.pasteservice.exception;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FailedToDecryptLinkException.class)
    public ResponseEntity<?> failedToDecryptLinkException(FailedToDecryptLinkException exception){
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(StorageUnavailableException.class)
    public ResponseEntity<?> storageUnavailableException(StorageUnavailableException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NoAccessForPasteException.class)
    public ResponseEntity<?> noAccessForPasteException(NoAccessForPasteException exception){
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(exception.getMessage());
    }
}
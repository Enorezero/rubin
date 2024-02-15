package ru.enorezero.pasteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UnhashableLinkException.class)
    public ResponseEntity<?> unhashableLinkException(UnhashableLinkException exception){
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(StorageUnavaibleException.class)
    public ResponseEntity<?> storageUnavaibleException(StorageUnavaibleException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(StorageUnavaibleException.class)
    public ResponseEntity<?> noAccessForPrivatePasteException(NoAccessForPrivatePasteException exception){
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(exception.getMessage());
    }
}

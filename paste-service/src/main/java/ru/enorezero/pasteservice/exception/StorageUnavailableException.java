package ru.enorezero.pasteservice.exception;

public class StorageUnavailableException extends RuntimeException{
    public StorageUnavailableException(String message) {
        super(message);
    }
}

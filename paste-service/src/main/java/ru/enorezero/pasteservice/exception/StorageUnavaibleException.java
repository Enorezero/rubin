package ru.enorezero.pasteservice.exception;

public class StorageUnavaibleException extends RuntimeException{
    public StorageUnavaibleException(String message) {
        super(message);
    }
}

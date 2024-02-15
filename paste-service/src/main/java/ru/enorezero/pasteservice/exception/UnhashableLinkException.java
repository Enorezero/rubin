package ru.enorezero.pasteservice.exception;

public class UnhashableLinkException extends RuntimeException{
    public UnhashableLinkException(String message) {
        super(message);
    }
}

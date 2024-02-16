package ru.enorezero.pasteservice.exception;

public class FailedToDecryptLinkException extends RuntimeException{
    public FailedToDecryptLinkException(String message) {
        super(message);
    }
}

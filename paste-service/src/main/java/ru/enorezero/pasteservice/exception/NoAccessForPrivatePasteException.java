package ru.enorezero.pasteservice.exception;

public class NoAccessForPrivatePasteException extends RuntimeException{
    public NoAccessForPrivatePasteException(String message) {
        super(message);
    }
}

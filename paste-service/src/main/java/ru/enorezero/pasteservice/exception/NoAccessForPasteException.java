package ru.enorezero.pasteservice.exception;

public class NoAccessForPasteException extends RuntimeException{
    public NoAccessForPasteException(String message) {
        super(message);
    }
}

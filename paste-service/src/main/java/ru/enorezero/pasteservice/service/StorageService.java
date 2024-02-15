package ru.enorezero.pasteservice.service;

public interface StorageService {
    String upload(final String bucketName, final String text);

    String download(final String bucketName, final String key);

    void delete(final String bucketName, final String key);
}

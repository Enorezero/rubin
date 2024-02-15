package ru.enorezero.pasteservice.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.enorezero.pasteservice.exception.StorageUnavaibleException;
import ru.enorezero.pasteservice.service.StorageService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private AmazonS3 s3Client;

    @Override
    public String upload(final String bucketName, final String text){
        //придумать как лучше называть объекты
        try {
            String textName = UUID.randomUUID().toString() + text.length();

            s3Client.putObject(bucketName, textName, text);

            return textName;
        }catch (SdkClientException exception){
            throw new StorageUnavaibleException("Хранилище недоступно");
        }
    }

    @Override
    public String download(final String bucketName, final String key) {
        try {
            S3Object s3Object = s3Client.getObject(bucketName, key);

            InputStream inputStream = s3Object.getObjectContent();

            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
        }catch (SdkClientException exception){
            throw new StorageUnavaibleException("Хранилище недоступно");
        }
    }

    @Override
    public void delete(final String bucketName, final String key) {
        try {
            s3Client.deleteObject(bucketName, key);
        }catch (SdkClientException exception){
            throw new StorageUnavaibleException("Хранилище недоступно");
        }
    }
}

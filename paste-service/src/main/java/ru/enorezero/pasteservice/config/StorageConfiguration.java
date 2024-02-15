package ru.enorezero.pasteservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {
    @Value("${cloud.aws.s3.credentials.access.key}")
    private String accessKey;

    @Value("${cloud.aws.s3.credentials.secret.key}")
    private String secretKey;

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "https://storage.yandexcloud.net", "ru-central1"
                        )
                )
                .build();
    }
}

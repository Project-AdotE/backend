package com.adote.api.infra.config.s3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private static final String BUCKET_NAME = "adote-api-bucket";
    private static final Region REGION = Region.US_EAST_2;

    public S3StorageService() {
        this.s3Client = S3Client.builder()
                .region(REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return String.format("https://%s.s3.%s.amazonaws.com/%s", BUCKET_NAME, REGION.id(), fileName);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload para o S3", e);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            String fileName = extractKeyFromUrl(fileUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo do S3: " + fileUrl, e);
        }
    }

    private String extractKeyFromUrl(String fileUrl) {
        String urlPrefix = String.format("https://%s.s3.%s.amazonaws.com/", BUCKET_NAME, REGION.id());
        if (fileUrl.startsWith(urlPrefix)) {
            return fileUrl.substring(urlPrefix.length());
        }
        throw new IllegalArgumentException("URL não corresponde ao formato esperado: " + fileUrl);
    }
}
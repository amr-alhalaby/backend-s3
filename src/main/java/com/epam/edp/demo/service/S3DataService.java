package com.epam.edp.demo.service;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Service
public class S3DataService {
    private final S3Client s3Client;
    private final String bucketName;
    private final String objectKey;

    public S3DataService(
        @Value("${s3.bucket-name}") String bucketName,
        @Value("${s3.object-key}") String objectKey,
        @Value("${s3.region}") String region
    ) {
        this.s3Client = S3Client.builder().region(Region.of(region)).build();
        this.bucketName = bucketName;
        this.objectKey = objectKey;
    }

    public String getDataContent() {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

            return s3Client.getObjectAsBytes(request).asString(StandardCharsets.UTF_8).stripTrailing();
        } catch (SdkException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to fetch S3 object", e);
        }
    }
}

package com.epam.edp.demo.service;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Service
public class S3DataService {
    private static final Logger log = LoggerFactory.getLogger(S3DataService.class);

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
            log.info("Fetching S3 object {}/{}", bucketName, objectKey);
            GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

            String content = s3Client.getObjectAsBytes(request).asString(StandardCharsets.UTF_8).stripTrailing();
            log.info("Fetched S3 object {}/{} ({} chars)", bucketName, objectKey, content.length());
            return content;
        } catch (SdkException e) {
            log.error("Failed to fetch S3 object {}/{}", bucketName, objectKey, e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to fetch S3 object", e);
        }
    }
}

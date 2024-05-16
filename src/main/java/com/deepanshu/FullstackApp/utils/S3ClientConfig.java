package com.deepanshu.FullstackApp.utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class S3ClientConfig {
//    private final String secretName;
//    @Getter
//    private final AmazonS3Client s3Client;
//
//    public S3ClientConfig(String secretName) throws Exception {
//        this.secretName = secretName;
//        this.s3Client = createS3Client();
//    }
//
//    private AmazonS3Client createS3Client() throws Exception {
//        AWSCredentialsProvider credentialsProvider = getCredentialsProvider();
//        return new AmazonS3Client.builder()
//                .withCredentialsProvider(credentialsProvider)
//                .withRegion(Regions.AP_SOUTH_1)  // Replace with your desired region
//                .build();
//    }
//
//    private AWSCredentialsProvider getCredentialsProvider() throws Exception {
//        AWSSecretsManager secretsManager = new AWSSecretsManager();
//
//        GetSecretValueRequest secretRequest = new GetSecretValueRequest().withSecretId(secretName);
//
//        Secret secret;
//        try {
//            secret = secretsManager.getSecretValue(secretRequest);
//        } catch (Exception e) {
//            throw new Exception("Error retrieving secret from Secrets Manager: " + e.getMessage());
//        }
//
//        String secretString = secret.getSecretString();
//
//        // Parse JSON string to extract credentials
//        ObjectMapper mapper = new ObjectMapper();
//        Credentials creds = mapper.readValue(secretString, Credentials.class);
//        return new com.amazonaws.auth.BasicAWSCredentials(creds.getAccessKeyId(), creds.getSecretAccessKey());
//    }

}

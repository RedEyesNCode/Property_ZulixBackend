package com.redeyesncode.estatespring.realestatebackend.aws;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public AmazonS3 s3Client() {
        String accessSecret = "qlW7kl7UPfWRvEKvWk69ecKMMfaVKV56us8l7s8Z";
        String accessKey = "AKIAUYTB63BPFNUX4HL7";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        String region = "ap-south-1";
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }
}

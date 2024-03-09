package com.techbuddy.goldendrop.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:properties/s3.properties")
@Configuration
@Data
public class AwsS3Config {

    @Value("${aws.s3.attachments.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.attachments.bucket.endpoint}")
    private String endPoint;

    @Value("${aws.s3.attachments.bucket.region}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
}

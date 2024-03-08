package com.techbuddy.goldendrop.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.techbuddy.goldendrop.configuration.AwsS3Config;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Service
@Data
public class AwsS3Service {

    private static final long MAX_READ_TIME = 1*60*60*1000;
    private static final long MAX_EXECUTION_TIME = 3*60*60*1000;
    public static String S3_FILE_SEPERATOR ="/";
    private final AmazonS3 amazonS3;
    private final AwsS3Config awsS3Config;

    @Autowired
    public AwsS3Service(AwsS3Config awsS3Config){
        this.amazonS3 = AmazonS3ClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(
                        awsS3Config.getEndPoint(), awsS3Config.getRegion()
                )
        ).withCredentials(
                new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsS3Config.getAccessKey(), awsS3Config.getSecretKey()
                        )
                )
        ).withPathStyleAccessEnabled(true).build();
        this.awsS3Config = awsS3Config;
    }

    public Optional<URL> putObject(String filePath, InputStream inputStream){
        ByteArrayInputStream byteArrayInputStream = null;
        Optional<URL> presignedUrl = Optional.empty();
        try{
            byte[] bytes = IOUtils.toByteArray(inputStream);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            PutObjectRequest putObjectRequest = new PutObjectRequest(awsS3Config.getBucketName(), filePath, byteArrayInputStream, metadata);
            this.amazonS3.putObject(putObjectRequest);
            presignedUrl = this.generatePreSignedUrlIfExists(awsS3Config.getBucketName(), filePath,
                    DateTime.now().plus(AwsS3Service.MAX_EXECUTION_TIME).toDate(), HttpMethod.GET);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try{
                if(byteArrayInputStream!=null) byteArrayInputStream.close();
            }catch (Exception ignore){

            }
        }
        return presignedUrl;
    }

    public Optional<URL> generatePreSignedUrlIfExists(String bucketName, String filePath, Date expiration, HttpMethod method){
        Optional<URL> returnURL = Optional.empty();
        ListObjectsV2Result result = this.amazonS3.listObjectsV2(bucketName, filePath);
        if(!result.getObjectSummaries().isEmpty()){
            returnURL = Optional.ofNullable(generatePreSignedURL(bucketName, filePath, expiration, method));
        }
        return returnURL;
    }

    public URL generatePreSignedURL(String bucket, String key, Date expiration, HttpMethod method){
        return this.amazonS3.generatePresignedUrl(bucket, key, expiration, method);
    }

    public URL generatePreSignedURL(String path) {
        return this.generatePreSignedURL(awsS3Config.getBucketName(), path, DateTime.now().plus(AwsS3Service.MAX_EXECUTION_TIME).toDate(), HttpMethod.GET);
    }

}

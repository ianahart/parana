package com.hart.backend.parana.amazon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class AmazonService {
    private AmazonS3 s3Client;

    @Value("${amazon.s3.default-bucket}")
    private String defaultRegion;

    @Value("${amazon.aws.access-key-id}")
    private String accessKeyId;

    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .withCredentials(getAwsCredentialProvider())
                .build();
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    private File convertMultipartFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convFile;
    }

    private String createFilename(String filename) {
        UUID uuid = UUID.randomUUID();
        return uuid + filename;
    }

    public void delete(String bucketName, String filename) {
        this.s3Client.deleteObject(new DeleteObjectRequest(bucketName, filename));
    }

    public String upload(String path, String filename, MultipartFile file) {
        try {
            File myFile = convertMultipartFile(file);
            String newFilename = createFilename(filename);

            this.s3Client.putObject(path, newFilename, myFile);
            this.s3Client.setObjectAcl(path, newFilename, CannedAccessControlList.PublicRead);
            myFile.delete();
            return newFilename;

        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to upload file to s3");
        }
    }

    public Map<String, String> getPublicUrl(String bucketName, String filename) {
        this.s3Client.setObjectAcl(bucketName, filename,
                CannedAccessControlList.PublicRead);
        URL url = this.s3Client.getUrl(bucketName, filename);
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("url", url.toString());
        hm.put("filename", filename);
        return hm;
    }
}

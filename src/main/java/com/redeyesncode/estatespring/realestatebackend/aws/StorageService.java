package com.redeyesncode.estatespring.realestatebackend.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.redeyesncode.estatespring.realestatebackend.models.common.StatusCodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service

public class StorageService {

    private String bucketName = "redeyesncodemaster";

    @Autowired
    private AmazonS3 s3Client;


    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.print("IO EXCEPTION..");
        }
        return convertedFile;
    }

    public ResponseEntity<?> uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // WE NEED TO WRITE THE ACL COMMANDS.
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));

        // GETTING THE S3 BUCKET - OBJECT WE JUST UPLOADED


        fileObj.delete();
        return ResponseEntity.ok(new StatusCodeModel("success", 200, AwsConstant.BUCKET_BASE_URL + fileName));

    }
}



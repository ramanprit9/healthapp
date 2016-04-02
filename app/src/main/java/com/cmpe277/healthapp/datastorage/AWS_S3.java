package com.cmpe277.healthapp.datastorage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;

/**
 * Created by Ramanprit Kaur on 10/14/2015.
 */
public class AWS_S3 {

    /* Note: - ACCESS_KEY_ID and SECRET_KEY may be incorrect
     *  An Exception will be thrown when trying to connect to AWS
     */
    public static AmazonS3 amazonS3_client;
    public static String s3Bucket = "healthapp.cholesterol.images";
    public static final String TAG = "#################";

    public static void setup()
    {
        System.out.println("**************** Setting up AWS S3");
        AWSCredentials credentials = new BasicAWSCredentials(AWS_Credentials.ACCESS_KEY_ID,
                AWS_Credentials.SECRET_KEY );

       /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
        *  An Exception will be thrown when trying to connect to AWS
        */

        amazonS3_client = new AmazonS3Client(credentials);

        //amazonS3_client.createBucket("healthapp.cholesterol.images");
    }

    public static void uploadImageToAmazonS3(String key, File file) {
        final PutObjectRequest request = new PutObjectRequest(s3Bucket, key, file);
       // final PutObjectResult result = amazonS3_client.putObject(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("**************** Uploading file to S3");
                //request.withCannedAcl(CannedAccessControlList.PublicRead); // public for all

            /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
             *  An Exception will be thrown when trying to connect to AWS
             */
            amazonS3_client.putObject(request); // upload file

            }
        }).start();

    }
}

package com.cmpe277.healthapp.datastorage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramanprit Kaur on 10/4/2015.
 */
public class AWS_SimpleDB {

    /* Note: - ACCESS_KEY_ID and SECRET_KEY may be incorrect
     * An Exception will be thrown when trying to connect to AWS
     */
    public static String CHOLESTRON_DOMAIN = "patient_cholesterol";
    public static String PATIENT_ID = "patient_id";
    public static String CHOLESTROL_ATTRIBUTE="cholesterol";

    private static AmazonSimpleDBClient sdbClient;

    public static void setupClient() {
        /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
        *  An Exception will be thrown when trying to connect to AWS
        */
        System.out.println("**************** Setting up client");
        AWSCredentials credentials = new BasicAWSCredentials( AWS_Credentials.ACCESS_KEY_ID,
                                AWS_Credentials.SECRET_KEY );
        sdbClient = new AmazonSimpleDBClient( credentials);
    }

    public static void addPatientInformation(final String patientID, final double cholesterol)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("**************** Adding attributes");
                ReplaceableAttribute patientIDAttr = new ReplaceableAttribute( PATIENT_ID, patientID, Boolean.TRUE );
                ReplaceableAttribute cholesterolAttr = new ReplaceableAttribute( CHOLESTROL_ATTRIBUTE, String.valueOf(cholesterol), Boolean.TRUE );

                List attrs = new ArrayList(2);
                attrs.add(patientIDAttr);
                attrs.add(cholesterolAttr);
                PutAttributesRequest par = new PutAttributesRequest( CHOLESTRON_DOMAIN, patientID, attrs);

               /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
                * An Exception will be thrown when trying to connect to AWS
                */
                sdbClient.putAttributes( par );
            }
        }).start();

    }
}

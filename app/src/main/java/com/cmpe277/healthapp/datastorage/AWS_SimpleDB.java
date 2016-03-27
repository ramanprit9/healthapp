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
    public static String PATIENT_INFO_DOMAIN = "patient_info";
    public static String PATIENT_ID = "patient_id";
    public static String CHOLESTROL_ATTRIBUTE="cholesterol";
    public static String NAME_ATTRIBUTE = "name";
    public static String AGE_ATTRIBUTE = "age";
    public static String GENDER_ATTRIBUTE = "gender";
    public static String WEIGHT_ATTRIBUTE = "weight";
    public static String SMOKER_ATTRIBUTE = "smoker";
    public static String HEART_DISEASE_ATTRIBUTE = "heart_disease";

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


    public static void addPatientInformation(final String name, final String patientID,
                                             final String age, final String gender, final String weight,
                                             final String smoker, final String heart_disease)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("**************** Adding attributes");
                ReplaceableAttribute patientIDAttr = new ReplaceableAttribute( PATIENT_ID, patientID, Boolean.TRUE );
                ReplaceableAttribute nameAttr = new ReplaceableAttribute( NAME_ATTRIBUTE, name, Boolean.TRUE );
                ReplaceableAttribute ageAttr = new ReplaceableAttribute( AGE_ATTRIBUTE, age, Boolean.TRUE );
                ReplaceableAttribute genderAttr = new ReplaceableAttribute( GENDER_ATTRIBUTE, gender, Boolean.TRUE );
                ReplaceableAttribute weightAttr = new ReplaceableAttribute( WEIGHT_ATTRIBUTE, weight, Boolean.TRUE );
                ReplaceableAttribute smokeAttr = new ReplaceableAttribute( SMOKER_ATTRIBUTE, smoker, Boolean.TRUE );
                ReplaceableAttribute heartAttr = new ReplaceableAttribute( HEART_DISEASE_ATTRIBUTE, heart_disease, Boolean.TRUE );

                List attrs = new ArrayList(2);
                attrs.add(patientIDAttr);
                attrs.add(nameAttr);
                attrs.add(ageAttr);
                attrs.add(genderAttr);
                attrs.add(weightAttr);
                attrs.add(smokeAttr);
                attrs.add(heartAttr);
                PutAttributesRequest par = new PutAttributesRequest( PATIENT_INFO_DOMAIN, patientID, attrs);

               /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
                * An Exception will be thrown when trying to connect to AWS
                */
                sdbClient.putAttributes( par );
            }
        }).start();

    }
}

package com.cmpe277.healthapp.datastorage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.cmpe277.healthapp.PatientInfo;
import com.cmpe277.healthapp.calibration.CholesterolEquation;
import com.cmpe277.healthapp.calibration.LinearEquation;
import com.echo.holographlibrary.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramanprit Kaur on 10/4/2015.
 */
public class AWS_SimpleDB {

    /* Note: - ACCESS_KEY_ID and SECRET_KEY may be incorrect
     * An Exception will be thrown when trying to connect to AWS
     */
    public static String CHOLESTEROL_DOMAIN = "patient_cholesterol";
    public static String PATIENT_INFO_DOMAIN = "patient_info";
    public static String PATIENT_ID = "patient_id";
    public static String CHOLESTEROL_ATTRIBUTE="cholesterol";
    public static String NAME_ATTRIBUTE = "name";
    public static String AGE_ATTRIBUTE = "age";
    public static String GENDER_ATTRIBUTE = "gender";
    public static String WEIGHT_ATTRIBUTE = "weight";
    public static String SMOKER_ATTRIBUTE = "smoker";
    public static String HEART_DISEASE_ATTRIBUTE = "heart_disease";
    public static String CHOLESTEROL_EQUATION_DOMAIN = "cholesterol_equation";
    public static String LIN_EQ_C = "C"; //constant
    public static String LIN_EQ_M = "m"; //slope
    public static String RGB_COMBO = "rgb_combo"; //rgb combination

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
                ReplaceableAttribute cholesterolAttr = new ReplaceableAttribute( CHOLESTEROL_ATTRIBUTE, String.valueOf(cholesterol), Boolean.TRUE );

                List attrs = new ArrayList(2);
                attrs.add(patientIDAttr);
                attrs.add(cholesterolAttr);
                PutAttributesRequest par = new PutAttributesRequest( CHOLESTEROL_DOMAIN, patientID, attrs);

               /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
                * An Exception will be thrown when trying to connect to AWS
                */
                sdbClient.putAttributes( par );
            }
        }).start();

    }


    public static void addPatientInformation(final String name, final String patientID,
                                             final String age, final String gender, final String weight,
                                             final String smoker, final String heart_disease) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("**************** Adding attributes");
                ReplaceableAttribute patientIDAttr = new ReplaceableAttribute(PATIENT_ID, patientID, Boolean.TRUE);
                ReplaceableAttribute nameAttr = new ReplaceableAttribute(NAME_ATTRIBUTE, name, Boolean.TRUE);
                ReplaceableAttribute ageAttr = new ReplaceableAttribute(AGE_ATTRIBUTE, age, Boolean.TRUE);
                ReplaceableAttribute genderAttr = new ReplaceableAttribute(GENDER_ATTRIBUTE, gender, Boolean.TRUE);
                ReplaceableAttribute weightAttr = new ReplaceableAttribute(WEIGHT_ATTRIBUTE, weight, Boolean.TRUE);
                ReplaceableAttribute smokeAttr = new ReplaceableAttribute(SMOKER_ATTRIBUTE, smoker, Boolean.TRUE);
                ReplaceableAttribute heartAttr = new ReplaceableAttribute(HEART_DISEASE_ATTRIBUTE, heart_disease, Boolean.TRUE);

                List attrs = new ArrayList(2);
                attrs.add(patientIDAttr);
                attrs.add(nameAttr);
                attrs.add(ageAttr);
                attrs.add(genderAttr);
                attrs.add(weightAttr);
                attrs.add(smokeAttr);
                attrs.add(heartAttr);
                PutAttributesRequest par = new PutAttributesRequest(PATIENT_INFO_DOMAIN, patientID, attrs);

               /* Note: - Correct ACCESS_KEY_ID and SECRET_KEY are not provided
                * An Exception will be thrown when trying to connect to AWS
                */
                sdbClient.putAttributes(par);
            }
        }).start();

    } //end addPatientInformation


    public static void fetchPatientInformation (final String id)
    {
        final PatientInfo patient = PatientInfo.get_patientInfo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SelectResult selectResult = null;
                String nextToken = null;
                String query = null;
                PatientInfo patientInfo = PatientInfo.getPatientInfo();

                do {
                    query = "select * from " + CHOLESTEROL_EQUATION_DOMAIN;
                    SelectRequest selectRequest = new SelectRequest(query);
                    selectRequest.setNextToken(nextToken);
                    selectResult = sdbClient.select(selectRequest);
                    nextToken = selectResult.getNextToken();
                    List<Item> list = selectResult.getItems();
                    String attrName, attrValue;
                    System.out.println("************** query = " + query);
                    System.out.println("**************** retrieving patient values");
                    if (list == null) System.out.println("************** list is null");
                    for(Item item: list){
                        item.getName(); // itemName
                        List<Attribute> listAttribute = item.getAttributes();
                        for(Attribute attribute:listAttribute){
                            attrName = attribute.getName(); // Attribute Name
                            attrValue = attribute.getValue(); // Attribute value;
                            System.out.printf("attrName = %s  attrValue = %s", attrName, attrValue);
                            if (attrName.equals(NAME_ATTRIBUTE)) {
                                patientInfo.setName(attrValue);
                                continue;
                            }
                            if (attrName.equals(AGE_ATTRIBUTE)) {
                                patientInfo.setAge(attrValue);
                                continue;
                            }
                            if (attrName.equals(GENDER_ATTRIBUTE)) {
                                patientInfo.setGender(attrValue);
                                continue;
                            }
                            if (attrName.equals(WEIGHT_ATTRIBUTE)) {
                                patientInfo.setWeight(attrValue);
                                continue;
                            }if (attrName.equals(SMOKER_ATTRIBUTE)) {
                                patientInfo.setSmoker(attrValue);
                                continue;
                            }
                            if (attrName.equals(HEART_DISEASE_ATTRIBUTE)) {
                                patientInfo.setHeart_disease(attrValue);
                                continue;
                            }
                        }
                    }
                } while (nextToken != null);
            }
        }).start();
    } //end fetchPatientInformation


    public void storeCholesterolEquation(double y, double C, double m)
    {
        CholesterolEquation.setY(y);
        CholesterolEquation.setC(C);
        CholesterolEquation.setM(m);
    }

    public static void fetchCholesterolEquation()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SelectResult selectResult = null;
                String nextToken = null;
                String query = null;

                do {
                    query = "select * from " + CHOLESTEROL_EQUATION_DOMAIN;
                    SelectRequest selectRequest = new SelectRequest(query);
                    selectRequest.setNextToken(nextToken);
                    selectResult = sdbClient.select(selectRequest);
                    nextToken = selectResult.getNextToken();
                    List<Item> list = selectResult.getItems();
                    String attrName, attrValue;
                    System.out.println("************** query = " + query);
                    System.out.println("**************** retrieving patient values");
                    if (list == null) System.out.println("************** list is null");
                    for(Item item: list){
                        item.getName(); // itemName
                        List<Attribute> listAttribute = item.getAttributes();
                        for(Attribute attribute:listAttribute){
                            attrName = attribute.getName(); // Attribute Name
                            attrValue = attribute.getValue(); // Attribute value;
                            if (attrName.equals(RGB_COMBO)) {
                                CholesterolEquation.setRGB_Combo(Integer.parseInt(attrValue));
                                continue;
                            }
                            if (attrName.equals(LIN_EQ_C)) {
                                CholesterolEquation.setC(Double.parseDouble(attrValue));
                                continue;
                            }
                            if (attrName.equals(LIN_EQ_M)) {
                                CholesterolEquation.setM(Double.parseDouble(attrValue));
                                continue;
                            }
                        }
                    }
                } while (nextToken != null);
            }
        }).start();
    } //end fetchCholesterolEquation
}

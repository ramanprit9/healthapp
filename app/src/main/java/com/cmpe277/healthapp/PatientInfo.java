package com.cmpe277.healthapp;

/**
 * Created by Ramanprit Kaur on 10/13/2015.
 */
public class PatientInfo {

    private static PatientInfo _patientInfo;

    private static String patient_id;
    private static String password;
    private static String name;
    private static String age;
    private static String gender;
    private static String weight;
    private static String smoker;
    private static String heart_disease;

    public static PatientInfo getPatientInfo()
    {
        if (_patientInfo == null) {
            _patientInfo = new PatientInfo();
            _patientInfo.setPatient_id("raman");
            _patientInfo.setPassword("password");
        }
        return _patientInfo;
    }

    public String getPatient_id() {
        return PatientInfo.patient_id;
    }

    public void setPatient_id(String id) {
        PatientInfo.patient_id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pswd) {
        PatientInfo.password = pswd;
    }

    public static PatientInfo get_patientInfo() {
        return _patientInfo;
    }

    public static void set_patientInfo(PatientInfo _patientInfo) {
        PatientInfo._patientInfo = _patientInfo;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        PatientInfo.name = name;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        PatientInfo.age = age;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        PatientInfo.gender = gender;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        PatientInfo.weight = weight;
    }

    public static String getSmoker() {
        return smoker;
    }

    public static void setSmoker(String smoker) {
        PatientInfo.smoker = smoker;
    }

    public static String getHeart_disease() {
        return heart_disease;
    }

    public static void setHeart_disease(String heart_disease) {
        PatientInfo.heart_disease = heart_disease;
    }
}

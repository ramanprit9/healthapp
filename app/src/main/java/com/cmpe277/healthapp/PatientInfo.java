package com.cmpe277.healthapp;

/**
 * Created by Ramanprit Kaur on 10/13/2015.
 */
public class PatientInfo {

    private static PatientInfo _patientInfo;

    private static String patient_id;

    public static PatientInfo getPatientInfo()
    {
        if (_patientInfo == null) {
            _patientInfo = new PatientInfo();
        }
        return _patientInfo;
    }

    public String getPatient_id() {
        return _patientInfo.getPatient_id();
    }

    public void setPatient_id(String patient_id) {
        _patientInfo.setPatient_id(patient_id);
    }

}

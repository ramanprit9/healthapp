package com.cmpe277.healthapp;

/**
 * Created by Ramanprit Kaur on 10/13/2015.
 */
public class PatientInfo {

    private static PatientInfo _patientIfno;

    private static String patient_id;

    public static PatientInfo getPatientInfo()
    {
        if (_patientIfno == null) {
            _patientIfno = new PatientInfo();
        }
        return _patientIfno;
    }

    public String getPatient_id() {
        return _patientIfno.getPatient_id();
    }

    public void setPatient_id(String patient_id) {
        _patientIfno.setPatient_id(patient_id);
    }

}

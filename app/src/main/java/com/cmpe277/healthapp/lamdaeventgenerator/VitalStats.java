package com.cmpe277.healthapp.lamdaeventgenerator;

/**
 * Created by Ramanprit Kaur on 11/22/2015.
 */
public class VitalStats {
    private boolean smokes;
    private boolean has_diabetes;
    private int blood_pressure_syst;
    private int blood_pressure_diast;
    private int total_cholesterol;
    private int bad_cholesterol;
    private boolean has_artery_disease;
    private boolean had_stent_placement;
    private boolean had_abdominal_aneurysm;
    private Heart_Attack_Common_Index heart_attack_family;
    private int bmi;

    public enum Heart_Attack_Common_Index {
        NOT_COMMON,
        SOMEWHAT_COMMON,
        QUITE_COMMON,
        EXTREMELY_COMMON
    }

    public VitalStats(boolean patient_smokes, boolean patient_has_diabetes, int patient_blood_pressure_syst,
                      int patient_blood_pressure_diast, int patient_total_cholesterol,
                      int patient_bad_cholesterol, boolean patient_has_artery_disease,
                      boolean patient_had_stent_replacement, boolean patient_had_abdominal_aneurysm,
                      Heart_Attack_Common_Index patient_heart_attack_family, int patient_bmi) {
        this.smokes = patient_smokes;
        this.has_diabetes = patient_has_diabetes;
        this.blood_pressure_syst = patient_blood_pressure_syst;
        this.blood_pressure_diast = patient_blood_pressure_diast;
        this.total_cholesterol = patient_total_cholesterol;
        this.bad_cholesterol = patient_bad_cholesterol;
        this.has_artery_disease = patient_has_artery_disease;
        this.had_stent_placement = patient_had_stent_replacement;
        this.had_abdominal_aneurysm = patient_had_abdominal_aneurysm;
        this.heart_attack_family = patient_heart_attack_family;
        this.bmi = patient_bmi;
    }

    public boolean isSmokes() {
        return smokes;
    }

    public void setSmokes(boolean smokes) {
        this.smokes = smokes;
    }

    public boolean isHas_diabetes() {
        return has_diabetes;
    }

    public void setHas_diabetes(boolean has_diabetes) {
        this.has_diabetes = has_diabetes;
    }

    public int getBlood_pressure_syst() {
        return blood_pressure_syst;
    }

    public void setBlood_pressure_syst(int blood_pressure_syst) {
        this.blood_pressure_syst = blood_pressure_syst;
    }

    public int getBlood_pressure_diast() {
        return blood_pressure_diast;
    }

    public void setBlood_pressure_diast(int blood_pressure_diast) {
        this.blood_pressure_diast = blood_pressure_diast;
    }

    public int getBad_cholesterol() {
        return bad_cholesterol;
    }

    public void setBad_cholesterol(int bad_cholesterol) {
        this.bad_cholesterol = bad_cholesterol;
    }

    public int getTotal_cholesterol() {
        return total_cholesterol;
    }

    public void setTotal_cholesterol(int total_cholesterol) {
        this.total_cholesterol = total_cholesterol;
    }

    public boolean isHas_artery_disease() {
        return has_artery_disease;
    }

    public void setHas_artery_disease(boolean has_artery_disease) {
        this.has_artery_disease = has_artery_disease;
    }

    public boolean isHad_stent_placement() {
        return had_stent_placement;
    }

    public void setHad_stent_placement(boolean had_stent_placement) {
        this.had_stent_placement = had_stent_placement;
    }

    public boolean isHad_abdominal_aneurysm() {
        return had_abdominal_aneurysm;
    }

    public void setHad_abdominal_aneurysm(boolean had_abdominal_aneurysm) {
        this.had_abdominal_aneurysm = had_abdominal_aneurysm;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }
}

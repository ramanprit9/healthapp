package com.cmpe277.healthapp;

/**
 * Created by owner on 4/2/2016.
 */
public class CalibResult {
    public char color;
    public double m;
    public double c;
    public double R2;

    public CalibResult() {
        color = 'R';
        m = 0;
        c = 0;
        R2 = 0;
    }

    public void setCalibration(char colorcode, double mval, double cval, double R2val) {
        color = colorcode;
        m = mval;
        c = cval;
        R2 = R2val;
    }

    public CalibResult getCalibration() {
        return this;
    }

}

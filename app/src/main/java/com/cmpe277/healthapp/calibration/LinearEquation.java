package com.cmpe277.healthapp.calibration;

/**
 * Created by Ramanprit Kaur on 3/30/2016.
 *
 * LinearEquation will contain the variables for the linear equation
 * The variables are static and will be fetched from SimpleDB
 */
public class LinearEquation {

    //y = mx + C
    //x = (y - C)/m
    private static double y; //not used for cholesterol eq b/c it will be based on RGB comb.
    private static double C;
    private static double m;

    public static double getY() {
        return y;
    }

    public static void setY(double y) {
        LinearEquation.y = y;
    }

    public static double getC() {
        return C;
    }

    public static void setC(double c) {
        C = c;
    }

    public static double getM() {
        return m;
    }

    public static void setM(double m) {
        LinearEquation.m = m;
    }
}

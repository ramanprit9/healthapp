package com.cmpe277.healthapp.calibration;

/**
 * Created by Ramanprit Kaur on 3/30/2016.
 */
public class CholesterolEquation extends LinearEquation {

    private static int RGB_Combo;

    /*
     * RGB_Combo indicates what combination of RGB is being used for the cholesterol equation
     * the 'y' values of the linear equation is calculation from the RGB combination
     */
    public static double getY(int R, int G, int B)
    {
        switch(RGB_Combo) {
            case 1:
                return R;
            case 2:
                return G;
            case 3:
                return B;
            default:
                return R;
        }
    }

    public static int getRGB_Combo() {
        return RGB_Combo;
    }

    public static void setRGB_Combo(int RGB_Combo) {
        CholesterolEquation.RGB_Combo = RGB_Combo;
    }

}

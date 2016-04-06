package com.cmpe277.healthapp;


import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Ramanprit Kaur on 1/31/2016.
 */
public class RGB_Result implements Serializable {
    public int R = 0;
    public int G = 0;
    public int B = 0;
    public double result = 0;
    //public Date date;


    public RGB_Result(int red, int green, int blue, double res) {
        R = red;
        G = green;
        B = blue;
        result = res;
    }

    public int getColor(char c) {
        if ('R' == c)
            return R;
        else if ('G' == c)
            return G;
        else if ('B' == c)
            return B;
        else
            return R;

    }

    //This main method is just for demonstrating and testing RGB_Result
    public void main(String[] args) {

        /* This shows how RGB_Result class will be used */
        ArrayList<RGB_Result> resultList = new ArrayList<>();
        resultList.add(new RGB_Result(200, 200, 200, 300));
        resultList.add(new RGB_Result(150, 200, 110, 250));

        int i = 0;
        int totalResults = resultList.size();
        for (i = 0; i < totalResults; i++) {
            System.out.printf("R = %d   B = %d  G = %d  result = %f\n",
                    resultList.get(i).R, resultList.get(i).G, resultList.get(i).B,
                    resultList.get(i).result);

        }
    }
}

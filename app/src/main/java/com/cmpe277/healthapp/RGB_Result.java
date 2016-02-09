package com.cmpe277.healthapp;


import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Ramanprit Kaur on 1/31/2016.
 */
public class RGB_Result {
    public int R;
    public int G;
    public int B;
    public double result;
    //public Date date;

    public RGB_Result(int red, int green, int blue, int res) {
        R = red;
        G = green;
        B = blue;
        result = res;
    }

    //This main method is just for demonstrating and testing RGB_Result
    public void main (String[] args) {

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

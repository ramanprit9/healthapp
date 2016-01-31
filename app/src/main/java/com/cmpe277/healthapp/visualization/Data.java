package com.cmpe277.healthapp.visualization;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ramanprit Kaur on 12/7/2015.
 */
public class Data {

    private ArrayList<TestResult> cholesterolResultList;

    public ArrayList<TestResult> getCholesterolResultList() {
        cholesterolResultList = new ArrayList<>();
        addToCholesterolResultList(new TestResult((new Date(2015, 5, 1)), 250.0));
        addToCholesterolResultList(new TestResult((new Date(2015, 6, 1)), 240.0));
        addToCholesterolResultList(new TestResult((new Date(2015, 7, 1)), 245.0));
        addToCholesterolResultList(new TestResult((new Date(2015, 8, 1)), 245.0));
        addToCholesterolResultList(new TestResult((new Date(2015, 9, 1)), 250.0));
        return cholesterolResultList;
    }

    public void addToCholesterolResultList(TestResult result) {
        cholesterolResultList.add(result);
    }

}

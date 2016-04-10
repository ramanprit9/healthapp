package com.cmpe277.healthapp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by owner on 4/8/2016.
 */
public class Instruction extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("*************************** In HomeActivity");
        setContentView(R.layout.activity_instruction);
    }
}

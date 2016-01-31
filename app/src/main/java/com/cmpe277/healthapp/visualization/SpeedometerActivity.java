/*******************************************************************************
 * Copyright (c) 2012 Evelina Vrabie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package com.cmpe277.healthapp.visualization;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe277.healthapp.R;

import org.codeandmagic.android.gauge.GaugeView;
import org.w3c.dom.Text;

import java.util.Random;

public class SpeedometerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

	private GaugeView mGaugeView1;
	private GaugeView mGaugeView2;
    private Spinner healthRiskSpinner;
    private TextView titleTxtView;
    private TextView txtPercentage;
    private int heartRisk;
    private int strokeRisk;
    private int vascularRisk;
    private int currentRiskPercent;

	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speedometer);

        this.setTitle("Health Risks");

        heartRisk = getIntent().getIntExtra("heartPercent", 50);
        strokeRisk = getIntent().getIntExtra("strokePercent", 50);
        vascularRisk = getIntent().getIntExtra("vascularPercent", 50);

        System.out.println("***************** In Speedometer Activity");
        System.out.printf("********************* heart = %d    stroke = %d   vasc = %d\n", heartRisk, strokeRisk, vascularRisk);
        titleTxtView = (TextView)findViewById(R.id.txtHealthRiskTitle);
        txtPercentage = (TextView)findViewById(R.id.txtPercent);

        //Set the gauge View
        mGaugeView1 = (GaugeView) findViewById(R.id.gauge_view1);

        //Items in the spinner
        String[] healthrisks_items_array =  {
                getResources().getString(R.string.heartattack),
                getResources().getString(R.string.stroke),
                getResources().getString(R.string.vasculardisease)};

        healthRiskSpinner = (Spinner) findViewById(R.id.spinnerHealthRisks);
        healthRiskSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, // Specify the layout to use when the list of choices appears
                healthrisks_items_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        healthRiskSpinner.setAdapter(adapter);
        healthRiskSpinner.setSelection(0); //by default select the first item in the spinner, which is at index 0

        Log.e("#######################", "speedometer activity created");
        System.out.println("####################### speedometer activity created");
    }


    /*
     * Timer to move the needle on GaugeView (Speedometer)
     * mTimerStart will usually be called when displaying new value on Speedometer
     * this way the new value will first go to 0 and then to the desired value
     * mTimerStart functional will usually be followed by mTimer
     */
    private final CountDownTimer mTimerStart = new CountDownTimer(30000, 30000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            mGaugeView1.setTargetValue(0); //start the Speedometer from 0
            txtPercentage.setText(0 + "%");
        }

        @Override
        public void onFinish() {}
    };

    //Timer to move the needle on GaugeView (Speedometer)
	private final CountDownTimer mTimer = new CountDownTimer(90000, 500) {

		@Override
		public void onTick(final long millisUntilFinished) {
            mGaugeView1.setTargetValue(0); //start the Speedometer from 0
            mGaugeView1.setTargetValue(currentRiskPercent);
            txtPercentage.setText(currentRiskPercent + "%");
        }

		@Override
		public void onFinish() {}
	};

    /*
     * This function is called when an Item selection in the HealthRisk Spinner is changed
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("#######################", "item sel changed");
        System.out.println("####################### item sel changed");

        String selectedItemStr = healthRiskSpinner.getSelectedItem().toString();

        //set the title on the screen
        titleTxtView.setText("Health Risk for " + selectedItemStr);

        Log.e("###", selectedItemStr );
        System.out.println("####################### item sel changed");

        if (selectedItemStr.equals(getResources().getString(R.string.heartattack))) {
            Log.e("#######################", "Heart Attack");
            System.out.println("####################### Heart Attack");
            currentRiskPercent = heartRisk;
            mTimer.start();
            return;
        }
        if (selectedItemStr.equals(getResources().getString(R.string.stroke))) {
            Log.e("#######################", "Stroke");
            System.out.println("####################### Stroke");
            currentRiskPercent = strokeRisk;
            mTimer.start();
            return;
        }
        if (selectedItemStr.equals(getResources().getString(R.string.vasculardisease))) {
            Log.e("#######################", "Vasc Disease");
            System.out.println("####################### Vasc Disease");
            currentRiskPercent = vascularRisk;
            mTimer.start();
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //This function (and onItemSelected function) has to be overrided when implementing OnItemSelectedListener
        //Since we don't do anything here, we simply return.
        return;
    }
}

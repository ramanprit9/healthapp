package com.cmpe277.healthapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cmpe277.healthapp.datastorage.AWS_SimpleDB;

/**
 * Created by sithara on 9/30/2015.
 */
public class PatientActivity extends AppCompatActivity {

    EditText editName;
    EditText editID;
    EditText editAge;
    EditText editGender;
    EditText editWeight;
    EditText editSmoker;
    EditText editHeartDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinfo);

        editName = (EditText) findViewById(R.id.eTxtPatientName);
        editID = (EditText) findViewById(R.id.eTxtPatientID);
        editAge = (EditText) findViewById(R.id.eTxtPatientAge);
        editGender = (EditText) findViewById(R.id.eTxtPatientGender);
        editWeight = (EditText) findViewById(R.id.eTxtPatientWeight);
        editSmoker = (EditText) findViewById(R.id.eTxtPatientSmoker);
        editHeartDisease = (EditText) findViewById(R.id.eTxtPatientHeartDisease);

        /*
         * populate the fields on the PatientInfo activity
         * The Patient Info data has already been fetched from SimpleDB in HomeActivity onCreate
         */
        populatePatientInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updatePatientInfo(View view)
    {
        AWS_SimpleDB.addPatientInformation(editName.getText().toString(), editID.getText().toString(),
                editAge.getText().toString(), editGender.getText().toString(),
                editWeight.getText().toString(), editSmoker.getText().toString(),
                editHeartDisease.getText().toString());

        //Populate the Patient Info field with latest changes
        AWS_SimpleDB.fetchPatientInformation(PatientInfo.getPatientInfo().getPatient_id());
    }

    public void populatePatientInfo()
    {
        PatientInfo patient = PatientInfo.get_patientInfo();
        editName.setText(patient.getName());
        editID.setText(patient.getPatient_id());
        editAge.setText(patient.getAge());
        editGender.setText(patient.getGender());
        editWeight.setText(patient.getWeight());
        editSmoker.setText(patient.getSmoker());
        editHeartDisease.setText(patient.getHeart_disease());
    }

    /*
    public void cancelPatientInfo(View view)
    {
        AWS_SimpleDB.fetchPatientInformation(PatientInfo.getPatientInfo().getPatient_id());
    }
    */
}

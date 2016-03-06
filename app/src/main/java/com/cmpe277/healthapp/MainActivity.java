package com.cmpe277.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cmpe277.healthapp.datastorage.AWS_SimpleDB;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

// Login screen
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Commenting out below part for setting up AWS.
         * Correct AWS ID and Secret Key are not being used. An exception will be thrown
         */
        //set up AWS SimpleDB
        AWS_SimpleDB.setupClient();

        //set up AWS S3
        //AWS_S3.setup();
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

    public void startHomeScreen(View view)
    {

        /*Intent intent = new Intent(this, VeenaGraphActivity.class);
        startActivity(intent);
        */
        /*********/
        System.out.println("********************* Going to HomeActivity");
        EditText username = (EditText) findViewById(R.id.edit_txt_username);
        String patient_id = username.getText().toString();
        //if (patient_id == null) {System.out.println("*****############### patient_id is null");}

        //Log.d("#################", "setting patient_id before starting HomeScreen");
        //PatientInfo.getPatientInfo().setPatient_id(patient_id);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
}

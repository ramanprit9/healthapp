package com.cmpe277.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.cmpe277.healthapp.dataanalysis.HealthRiskCalculator;
import com.cmpe277.healthapp.datastorage.AWS_Credentials;
import com.cmpe277.healthapp.datastorage.AWS_SimpleDB;
import com.cmpe277.healthapp.lamdaeventgenerator.MyInterface;
import com.cmpe277.healthapp.lamdaeventgenerator.VitalStats;
import com.cmpe277.healthapp.visualization.PastTestResults;
import com.cmpe277.healthapp.visualization.SpeedometerActivity;

/**
 * Created by Ramanprit Kaur on 10/4/2015.
 */
//Screen with the all the options (New Test, Past Results, etc....)
public class HomeActivity extends AppCompatActivity {

    public static final int HEART_INDEX = 0;
    public static final int STROKE_INDEX = 1;
    public static final int VASC_INDEX = 2;
    public static final int TOTAL_DISEASES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("*************************** In HomeActivity");
        setContentView(R.layout.activity_homepage);

        /*Action Bar*/
        android.support.v7.app.ActionBar ab=getSupportActionBar();
        ab.setLogo(R.drawable.ic_launcher);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        /*
         * Fetch the Patient Info from SimpleDB
         * The reasons we are fetching here:
         *  1. Need to fetch only once when the user logs in otherwise we have to pay more for AWS
         *  2. It takes some time to fetch from AWS since it happens in a different thread so
         *     we want to fetch it ahead of time
         */
        //AWS_SimpleDB.fetchPatientInformation(PatientInfo.getPatientInfo().getPatient_id());
        //AWS_SimpleDB.fetchCholesterolEquation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.home_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout_id:
                Toast.makeText(getApplicationContext(), "logging off", Toast.LENGTH_SHORT).show();
                finish();
            case R.id.instructions:
                Toast.makeText(getApplicationContext(), "instructions", Toast.LENGTH_SHORT).show();
                finish();
                //Intent intent = new Intent(this, PatientActivity.class);
                //startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    /* Open the Patient Vitals screen */
    public void showPatientVitals (View view)
    {
        Intent intent = new Intent(this, PastTestResults.class);
        startActivity(intent);
    }

    /* Open the New Test screen*/
    public void startTestScreen(View v) {
        Intent intent = new Intent(this, TestoptionsActivity.class);
        startActivity(intent);
    }

    /* Open the Patient Info screen */
    public void startPatientScreen(View v) {
        Intent intent = new Intent(this, PatientActivity.class);
        startActivity(intent);
    }


    /* Open up the screen for calibration
     * This is where the user will start taking sample pictures.
     */
    public void startCalibrationScreen(View v) {
        Intent intent = new Intent(this, CalibCameraActivity.class);
        startActivity(intent);
    }

    /*
     * Get the AWS credentials and then call the
     * Invoke Lambda to calculate the risk
     * Call the HealthRiskCalculator to get the health risks
     */
    public void calculateHealthRisks(View view) {

        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), AWS_Credentials.HEALTHCALC_IDENTITYPOOLID, Regions.US_EAST_1);

        // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                Regions.US_EAST_1, cognitoProvider);

        // Create the Lambda proxy object with a default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder.
        final MyInterface myInterface = factory.build(MyInterface.class);

        Log.e("##########", "HomeActivity - about to call health cal");

        //TODO: Un-comment the Lambda call
        //calculateHealthRisksFromLambda(myInterface);

        //TODO: Comment the below call when invoking from Lambda
         //directly call showHealthRisks (like below) to avoid invoking Lambda (avoiding AWS Costs)
        showHealthRisks (80, 50, 70);
    }


    public void calculateHealthRisksFromLambda(final MyInterface myInterface) {

        Log.e("###############", "caculateHealthRisksFromLamda");
        VitalStats vitalStats = HealthRiskCalculator.getHeartAttackVitalStats();

        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        new AsyncTask<VitalStats, Void, Double[]>() {
            @Override
            protected Double[] doInBackground(VitalStats... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                Double results[] = new Double[TOTAL_DISEASES];
//                results[HEART_INDEX] = 0.0;
//                results[STROKE_INDEX] = 0.0;
//                results[VASC_INDEX] = 0.0;
                try {
                    results[HEART_INDEX] = myInterface.calculate_heart_attack_risk(params[0]);
                    results[STROKE_INDEX] = myInterface.calculate_stroke_risk(params[0]);
                    results[VASC_INDEX] = myInterface.calculate_vascular_disease_risk(params[0]);

                    results[STROKE_INDEX] = results[HEART_INDEX] * .80;
                    results[VASC_INDEX] = results[HEART_INDEX] * .90;
                    return results;
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    System.out.println("****************************** failed to invoke echo");
                    return results;
                }
            }

            @Override
            protected void onPostExecute(Double[] results) {
                System.out.println("******************************* onPostExecute results[0] " + results[0]);
                Log.e("###############", "postExecute success");
                showHealthRisks(results[HEART_INDEX].intValue(),
                        results[STROKE_INDEX].intValue(), results[VASC_INDEX].intValue());

            }
        }.execute(vitalStats);

    }

    /*
     * This method will be called to display the Speedometer Activity with the health
     * risk percentage.
     */
    public void showHealthRisks (int heart, int stroke, int vascular)
    {
        Log.e("###############", "HomeActivity - showHealthRisks");
        Intent intent = new Intent(this, SpeedometerActivity.class);
        intent.putExtra("heartPercent", heart);
        intent.putExtra("strokePercent", stroke);
        intent.putExtra("vascularPercent", vascular);

        Log.e("###############", "HomeActivity - start speedometer");
        startActivity(intent);
    }

    public void startCommScreen(View view)
    {
        Intent intent = new Intent(this, CommunicationActivity.class);
        startActivity(intent);
    }
}

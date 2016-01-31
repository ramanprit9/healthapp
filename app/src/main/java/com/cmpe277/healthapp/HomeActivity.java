package com.cmpe277.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.cmpe277.healthapp.dataanalysis.HealthRiskCalculator;
import com.cmpe277.healthapp.datastorage.AWS_Credentials;
import com.cmpe277.healthapp.lamdaeventgenerator.MyInterface;
import com.cmpe277.healthapp.lamdaeventgenerator.VitalStats;
import com.cmpe277.healthapp.visualization.PastTestResults;
import com.cmpe277.healthapp.visualization.SpeedometerActivity;

/**
 * Created by Ramanprit Kaur on 10/4/2015.
 */
public class HomeActivity extends Activity {

    public static final int HEART_INDEX = 0;
    public static final int STROKE_INDEX = 1;
    public static final int VASC_INDEX = 2;
    public static final int TOTAL_DISEASES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("*************************** In HomeActivity");
        setContentView(R.layout.activity_homepage);
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

    public void showPatientVitals (View view)
    {
        Intent intent = new Intent(this, PastTestResults.class);
        startActivity(intent);
    }

    public void startTestScreen(View v) {
        Intent intent = new Intent(this, TestoptionsActivity.class);
        startActivity(intent);
    }

    public void startPatientScreen(View v) {
        Intent intent = new Intent(this, PatientActivity.class);
        startActivity(intent);
    }

    /*
     * Get the AWS credentials and then call the
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

        calculateHealthRisksFromLambda(myInterface);

         //Testing commit
         //directly call showHealthRisks (like below) to avoid invoking Lambda
        //showHealthRisks (80, 50, 70);
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
                try {
                    results[HEART_INDEX] = myInterface.calculate_heart_attack_risk(params[0]);
                    //results[STROKE_INDEX] = myInterface.calculate_stroke_risk(params[0]);
                    //results[VASC_INDEX] = myInterface.calculate_vascular_disease_risk(params[0]);

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

}

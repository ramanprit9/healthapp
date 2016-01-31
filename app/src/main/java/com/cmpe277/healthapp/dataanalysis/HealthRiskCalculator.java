package com.cmpe277.healthapp.dataanalysis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.cmpe277.healthapp.HomeActivity;
import com.cmpe277.healthapp.lamdaeventgenerator.MyInterface;
import com.cmpe277.healthapp.lamdaeventgenerator.VitalStats;
import com.cmpe277.healthapp.visualization.SpeedometerActivity;

/**
 * Created by Ramanprit Kaur on 11/22/2015.
 *
 * This class will invoke AWS Lambda to get Health Risks associated with patient
 * based on the patient's vital stats
 */
public class HealthRiskCalculator extends Activity{

    private static int heartAttackRisk;
    private static int strokeRisk;
    private static int vascularDiseaseRisk;

    private static final int HEART_INDEX = 0;
    private static final int STROKE_INDEX = 1;
    private static final int VASC_INDEX = 2;
    private static final int TOTAL_RISKS = 3;

    public static VitalStats getHeartAttackVitalStats() {
        boolean smokes = true;
        boolean has_diabetes = true;
        int blood_pressure_syst = 170;
        int blood_pressure_diast = 105;
        int total_cholesterol = 245;
        int bad_cholesterol = 195;
        boolean has_artery_disease = false;
        boolean had_stent_placement = false;
        boolean had_abdominal_aneurysm = true;
        VitalStats.Heart_Attack_Common_Index heart_attack_family = VitalStats.Heart_Attack_Common_Index.SOMEWHAT_COMMON;
        int bmi = 32;

        VitalStats vs = new VitalStats(smokes, has_diabetes, blood_pressure_syst, blood_pressure_diast,
                total_cholesterol, bad_cholesterol, has_artery_disease, had_stent_placement,
                had_abdominal_aneurysm, heart_attack_family, bmi);
        return vs;
    }


    public Double calculateHealthRisks(final MyInterface myInterface) {

        String message = "hello";
        System.out.println("****************************** message = " + message.toString());
        Log.e("###############", "caculateHealthRisks in calculator");

        VitalStats vitalStats = getHeartAttackVitalStats();

        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        new AsyncTask<VitalStats, Void, Double>() {
            @Override
            protected Double doInBackground(VitalStats... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.calculate_heart_attack_risk(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    System.out.println("****************************** failed to invoke echo");
                    return -1.0;
                }
            }

            @Override
            protected void onPostExecute(Double result) {
                if (result == -1.0) {
                    System.out.println("*********************** returned -1.0");
                }
                System.out.println("******************************* onPostExecute result " + result);
                Log.e("###############", "postExecute success");
                int resultInt = result.intValue();
                Intent intent = new Intent(HealthRiskCalculator.this, SpeedometerActivity.class);
                //intent.putExtra("strokePercent", stroke);
                //intent.putExtra("vascularPercent", vascular);

                Log.e("###############", "HomeActivity - start speedometer");
                startActivity(intent);

            }
        }.execute(vitalStats);

        return 0.0;
    }

    public static void setHealthRiskValues(Double heart, Double stroke, Double vascular) {

        System.out.println("***************************** setHealthRiskValues in HealthRiskCalculator");
        Log.e("########", "setHealthRiskValues in HealthRiskCalculator");

        heartAttackRisk = heart.intValue();
        strokeRisk = stroke.intValue();
        vascularDiseaseRisk = vascular.intValue();

        HomeActivity activity = new HomeActivity();
        Log.e("########", "setHealthRiskValues in HealthRiskCalculator");
        activity.showHealthRisks(heartAttackRisk, strokeRisk, (vascularDiseaseRisk+20));
    }
}

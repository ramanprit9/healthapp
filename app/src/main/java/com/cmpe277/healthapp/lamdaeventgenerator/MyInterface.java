package com.cmpe277.healthapp.lamdaeventgenerator;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/**
 * Created by Ramanprit Kaur on 11/21/2015.
 */
public interface MyInterface {
    /**
     * Invoke the Lambda function "lambda_hanlder".
     * The function name is the method name
     */
    @LambdaFunction
    String lamda_handler(PersonInfo person);

    /**
     * Invoke the Lambda function "calculate_heart_attack_risk".
     * The function name is the method name
     */
    //@LambdaFunction
    Double calculate_heart_attack_risk(VitalStats vitalStats);

    /**
     * Invoke the Lambda function "calculate_stroke_risk".
     * The function name is the method name
     */
    @LambdaFunction
    Double calculate_stroke_risk(VitalStats vitalStats);

    /**
     * Invoke the Lambda function "calculate_vascular_disease_risk".
     * The function name is the method name
     */
    @LambdaFunction
    Double calculate_vascular_disease_risk(VitalStats vitalStats);

}

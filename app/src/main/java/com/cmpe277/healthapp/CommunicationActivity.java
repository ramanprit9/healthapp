package com.cmpe277.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cmpe277.healthapp.datastorage.AWS_S3;
import com.cmpe277.healthapp.datastorage.AWS_SimpleDB;

/**
 * Created by Ramanprit Kaur on 4/1/2016.
 */
public class CommunicationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
        finish();
    }
    public void phoneCall(View view) {
        String phno="10digits";
        Intent i=new Intent(Intent.ACTION_DIAL,Uri.parse(phno));
        i.setData(Uri.parse("tel:0123456789"));
        startActivity(i);

    }
}

package com.cmpe277.healthapp;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FinalResultActivity extends AppCompatActivity {
    TextView tv;
    ProgressBar pBar;
    int pStatus = 0;
    String chlStr;

    //iterations in the loop for progress bar to complete
    public static final int PROGRESSBAR_ITERATIONS = 100;

    //milliseconds the progress bar thread is going to sleep during each iteration
    public static final int PROGRESSBAR_ITER_SLEEP_MS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalresult);

        tv = (TextView) findViewById(R.id.txtProgressPercent);
        pBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Get the cholesterol passed from CameraActivity
        chlStr = Integer.toString(getIntent().getExtras().getInt("cholesterol"));
        chlStr += "  mg/dl";

        System.out.println("choesterol = " + chlStr);

        //Progress Bar running
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus <= PROGRESSBAR_ITERATIONS) {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pBar.setProgress(pStatus);
                            pBar.setSecondaryProgress(pStatus + 5);
                            tv.setText(pStatus + "/" + pBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(PROGRESSBAR_ITER_SLEEP_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++pStatus;

                }

                //Call the handler to set the result value
                Message msg = Message.obtain();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            TextView txtResult;
            TextView txtWaitMessage;  //TextView which displayes the message "Please wait..."
                                    // while progress bar is loading

            //EditText for showing the test (cholesterol) test
            txtResult = (TextView) findViewById(R.id.txtResult);
            txtWaitMessage = (TextView) findViewById(R.id.txtProgressMsg);

            //Set the cholesterol value in EditText field
            txtResult.setText(chlStr);

            //Change the ProgressBar message from "Please Wait ...." to Done
            txtWaitMessage.setText("Done");
        }

    };

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

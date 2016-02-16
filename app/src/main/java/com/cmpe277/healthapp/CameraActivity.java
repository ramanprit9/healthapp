package com.cmpe277.healthapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import com.cmpe277.healthapp.datastorage.AWS_S3;
import com.cmpe277.healthapp.datastorage.AWS_SimpleDB;

/**
 * Created by Ramanprit Kaur on 10/4/2015.
 */
public class CameraActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private Bitmap imageBitmap;
    private ImageView mImageView;
    DragRectView rectView;
    //private File bitMapImage_file;

    private int selectedAreaX1, selectedAreaY1, selectedAreaX2, selectedAreaY2;


    public enum Image_Type {
        REFERENCE_IMAGE,
        SAMPLE_IMAGE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("#################", " onCreate() camera activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mImageView = (ImageView) findViewById(R.id.imgView);
        rectView = (DragRectView) findViewById(R.id.dragRect);

        if (null != rectView) {
            rectView.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {
                    //Save the coordinates of the rectangle in private variables
                    selectedAreaX1 = rect.left;
                    selectedAreaY1 = rect.top;
                    selectedAreaX2 = rect.right;
                    selectedAreaY2 = rect.bottom;

                    Toast.makeText(getApplicationContext(), "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String getPatientID()
    {
        int min = 100000;
        int max = 200000;
        Random rn = new Random();
        int range = max - min + 1;
        int randomNum =  rn.nextInt(range) + min;
        //return Integer.toString(randomNum);
        return "106257";
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

    public void takePicture(View view) {

        //Enable the Drag Rectangle, which is select the portion of the image
        rectView.setVisibility(View.VISIBLE);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }



    /* for capturing the image taken using the camera */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("#################", " in beginning of onActivityResult");
        //Check that request code matches ours:
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE)
        {
            Log.d("#################", " CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE");

            //Get our saved file into a bitmap object:
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");

            Log.d("#################", " calling decodeSampledBitmapFromFile");
            Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 600, 900);

            Log.d("#################", " setting ImageBitmap");
            mImageView.setImageBitmap(bitmap);
        }
    }


    /* saving a large size image taken from the camera */
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        Log.d("#################", " in beginning of decodeSampleBitmapFromFile");

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        Log.d("#################", " calculate inSampleSize");

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Log.d("#################", " returning decodeFile");

        return BitmapFactory.decodeFile(path, options);
    }

    public void calculateValue(View view)
    {
        Log.d("#################", " calculateRGBValue");

        View rootView = (View) findViewById(R.id.rootLayout);
        Bitmap image = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        rootView.draw(canvas);

        System.out.printf("############# image dimensions: width = %d   height = %d",
                mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());

        if (image == null) { System.out.println("##################### image is null"); }
        getRGB(image);
    }


    public void getRGB(Bitmap image)
    {
        int width = selectedAreaX2 - selectedAreaX1;
        int height = selectedAreaY2 - selectedAreaY1;

        System.out.printf("################### (%d, %d, %d, %d\n", selectedAreaX1, selectedAreaY1,
                selectedAreaX2, selectedAreaY2);
        System.out.printf("################### width = %d   height = %d\n", width, height);
        calculateAverageRGB(image, selectedAreaX1, selectedAreaY1, width, height);
    }

    /* Calculate the average RGB of the selected area
     * @image - Bitmap image whose RGB value to be retrieve
     * int cornerX - x value of the area where to start getting the RGB value
     * int cornerY - y value of the area where to start getting the RGB value
     * int picw - total width of the area whose RGB value to be retrieved
     * int pich - total height of the area whose RGB value to be retrieved
     */
    public void calculateAverageRGB(Bitmap image, int cornerX, int cornerY,
                                    int picw, int pich)
    {
        int totalPixels = picw * pich;
        int[] pix = new int[picw * pich];
        image.getPixels(pix, 0, picw, cornerX,
                cornerY, picw, pich);

        //R,G,B - Red, Green, Blue
        int R, G, B, totalR, totalG, totalB, avgR, avgG, avgB;
        totalR = totalG = totalB = 0;

        for (int y = 0; y < pich; y++){
            for (int x = 0; x < picw; x++)
            {
                int index = y * picw + x;

                //bitwise shifting
                R = (pix[index] >> 16) & 0xff;
                G = (pix[index] >> 8) & 0xff;
                B = pix[index] & 0xff;

                totalR = totalR + R;
                totalG = totalG + G;
                totalB = totalB + B;

                //to restore the values after RGB modification
                pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }}

        avgR = totalR / totalPixels;
        avgG = totalG / totalPixels;
        avgB = totalB / totalPixels;

        Log.d("###############", "R G B = " + avgR + " " + avgG + " " + avgB);
        calculateCholesterol(avgR, avgG, avgB);
    }

    /*
     * Calculate the cholesterol based on the given R, G, and B value
     * For now, the equation being used is fixed....
     * Once calibration is implemented, get the equation calculated
     */
    public void calculateCholesterol(int R, int G, int B)
    {
        //x-axis - concentration/cholesterol
        //y-axis - either R/G/B or a combination of RGB
        double y = B; //or B/(R+G+B);

        //y = 0.1529x + 0.4243
        //x = (y - 0.4243)/0.1529   [where x is the cholesterol]
        double cholesterol = (y - 0.4243)/0.1529 - 170;
        Log.d("###############", "int cholesterol = " + cholesterol);

        //Add the patient cholesterol value in the AWS SimpleDB
        String patient_id = getPatientID();
        if (patient_id == null) { Log.d("###############", "patient_id is null ");}

        //Commenting out below part so we don't have to pay for the AWS
        //AWS_SimpleDB.addPatientInformation(patient_id, (int)Math.round(cholesterol));

        Intent intent = new Intent(this, FinalResultActivity.class);
        intent.putExtra("cholesterol", (int)Math.round(cholesterol));
        Log.d("###############", "int cholesterol = " + (int)Math.round(cholesterol));
        startActivity(intent);
    }

    /*
     * Store image in the cloud
     */
    public void storeImageInCloud(View view)
    {
        String filename = "blah";
        //create a file to write bitmap data
        File file = new File(getApplicationContext().getCacheDir(), filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View rootView = (View) findViewById(R.id.rootLayout);
        Bitmap image = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        rootView.draw(canvas);


        //Convert bitmap to byte array
        Bitmap bitmap = image;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //AWS_S3.uploadImageToAmazonS3(getPatientID(), file);
    }

}


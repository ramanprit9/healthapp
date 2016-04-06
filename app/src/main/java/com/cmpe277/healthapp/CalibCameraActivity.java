package com.cmpe277.healthapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ramanprit Kaur on 1/31/2016.
 */
public class CalibCameraActivity extends AppCompatActivity {

    ArrayList<RGB_Result> sample_result_list = new ArrayList<RGB_Result>();
    int currentSampleIndex; //number of the image that is currently being captured
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private Bitmap imageBitmap;
    private ImageView mImageView;
    DragRectView rectView;
    EditText editTextChls;
    //private File bitMapImage_file;

    private int selectedAreaX1, selectedAreaY1, selectedAreaX2, selectedAreaY2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("#################", " onCreate() camera activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calib_camera);

        currentSampleIndex = 0;
        mImageView = (ImageView) findViewById(R.id.imgView);
        rectView = (DragRectView) findViewById(R.id.dragRect);
        editTextChls = (EditText) findViewById(R.id.eTxtCholestrol);

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
    private static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
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


    private void getRGB(Bitmap image)
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
    private void calculateAverageRGB(Bitmap image, int cornerX, int cornerY,
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


        /* Update the results/calculations/values in the sample_result_list */
        /* The purpose of inserting at currentSampleIndex is to avoid adding result of one image
           multiple times to the list; in case the Result button is clicked multiple times */
        sample_result_list.add(currentSampleIndex,
                new RGB_Result(avgR, avgG, avgB,
                        Double.parseDouble(editTextChls.getText().toString())));

        Log.d("###############", "R G B i = " + avgR + " " + avgG + " " + avgB + currentSampleIndex);
    }


    /* Take the next sample image */
    public void nextImage (View view) {
        System.out.println("**************************** Clearing out the image to null");
        //Remove the image taken for the previous sample
        mImageView.setImageDrawable(null);

        //Increment the currentSampleIndex number to move to to the next image number
        currentSampleIndex++;
    }

    public void printResults()
    {
        int i = 0;
        int totalResults = sample_result_list.size();
        System.out.println("******************************************");
        for (i = 0; i < totalResults; i++) {
            System.out.printf("R = %d   B = %d  G = %d  result = %f\n",
                    sample_result_list.get(i).R, sample_result_list.get(i).G,
                    sample_result_list.get(i).B,
                    sample_result_list.get(i).result);

        }
    }

    /* Done taking all the sample images. Send over the results to generate the equation */
    public void generateEquation(View view)
    {
        //printResults();
        Intent intent = new Intent(this, CalibrateActivity.class);
        intent.putExtra("Sample_list_extra",sample_result_list);
        //intent.putParcelableArrayListExtra("Sample_list_extra", (ArrayList<? extends Parcelable>) sample_result_list);
        //startActivityForResult(intent, this.sample_result_list);
        startActivity(intent);

        //Pass sample_result_list to the your acitvity (Equation Generation)
        //Create new intent and pass the list there

    }
}

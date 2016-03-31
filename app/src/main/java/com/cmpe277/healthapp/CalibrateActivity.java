package com.cmpe277.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by owner on 3/16/2016.
 */
public class CalibrateActivity extends Activity {
    ListView rgb_table_view;
    ArrayList<RGB_Result> resultList;
    //Intent intent =new Intent();
   // ArrayList sample_list = new ArrayList<>();
    //sample_list= (ArrayList<RGB_Result>)getIntent().getSerializableExtra("Sample_list_extra");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        rgb_table_view = (ListView) findViewById(R.id.mytable);

       /*This shows how RGB_Result class will be used*/
        Intent intent =getIntent();
        //sample_list =  (ArrayList<? extends Parcelable>)intent.getParcelableArrayListExtra("Sample_list_extra");
       // sample_list = getIntent().getExtras().getParcelableArrayList("Sample_list_extra");
        //sample_list= (ArrayList<RGB_Result>)getIntent().getSerializableExtra("Sample_list_extra");
        resultList = new ArrayList<>();
        resultList.add(new RGB_Result(200, 200, 200, 300));
        resultList.add(new RGB_Result(150, 200, 110, 250));
        resultList.add(new RGB_Result(210, 206, 200, 275));
        resultList.add(new RGB_Result(220, 208, 110, 250));
        resultList.add(new RGB_Result(215, 209, 200, 280));


        /* populate the ArrayAdapter for the Data List */
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        //private String arr[]=toArray(resultList);
        //arr = resultList.toArray(new String[resultList.size()]);
        //my_table_view.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , arr));

        populateList();

        /******************graph************************************************
        int i = 0;
        int totalResults = resultList.size();
        /*GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(resultList.get(0).R, resultList.get(0).result),
                new DataPoint(resultList.get(1).R, resultList.get(1).result),
                new DataPoint(resultList.get(2).R, resultList.get(2).result),
                new DataPoint(resultList.get(3).R, resultList.get(3).result),
                new DataPoint(resultList.get(4).R, resultList.get(4).result)
        });

        graph.addSeries(series);
        for (i = 0; i < totalResults; i++) {
            System.out.printf("R = %d   B = %d  G = %d  result = %f\n",
                    resultList.get(i).R, resultList.get(i).G, resultList.get(i).B,
                    resultList.get(i).result);


        }*/
        /*********************************************************************/

    }

    private void populateList() {
        /* populate the ArrayAdapter for the Data List */
        ArrayAdapter<RGB_Result> adapter = new DataListAdapter();
        rgb_table_view.setAdapter(adapter);
    }

    private class DataListAdapter extends ArrayAdapter<RGB_Result> {
        public DataListAdapter() {
            super (CalibrateActivity.this, R.layout.data_list, resultList);
            //super (CalibrateActivity.this, R.layout.data_list, sample_list);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.data_list, parent, false);
            RGB_Result currentRGBREsult = resultList.get(position);
           // RGB_Result currentRGBREsult = sample_list.get(position);
            TextView rValue = (TextView) view.findViewById(R.id.txtRValue);
            rValue.setText(Integer.toString(currentRGBREsult.R));
            TextView gValue = (TextView) view.findViewById(R.id.txtGValue);
            gValue.setText(Integer.toString(currentRGBREsult.G));
            TextView bValue = (TextView) view.findViewById(R.id.txtBValue);
            bValue.setText(Integer.toString(currentRGBREsult.B));
            TextView concValue = (TextView) view.findViewById(R.id.txtConcValue);
            concValue.setText(Double.toString(currentRGBREsult.result));

            return view;
        }
    }

    public void drawGraphTest(View view) {
        Intent intent = new Intent(this, VeenaGraphActivity.class);
        startActivity(intent);

    }
}
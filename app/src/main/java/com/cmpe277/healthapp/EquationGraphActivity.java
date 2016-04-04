package com.cmpe277.healthapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;
import static com.cmpe277.healthapp.BestFit.linear_regression;




/**
 * Created by owner on 2/17/2016.
 **/
public class EquationGraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        /*This shows how RGB_Result class will be used*/
        ArrayList<RGB_Result> resultList = new ArrayList<>();
        resultList.add(new RGB_Result(100, 205, 180, 250));
        resultList.add(new RGB_Result(150, 270, 225, 275));
        resultList.add(new RGB_Result(150, 330, 206, 295));
        resultList.add(new RGB_Result(240, 250, 250, 245));


        int i = 0;
        int totalResults = resultList.size();
        for (i = 0; i < totalResults; i++) {
            System.out.printf("R = %d   B = %d  G = %d  result = %f\n",
                    resultList.get(i).R, resultList.get(i).G, resultList.get(i).B,
                    resultList.get(i).result);

        }
        /*******************************************************************************
         * Find R value using linear regression method*
        *********************************************************************************/
        int j = 0;
        //global result object
        CalibResult calib=new CalibResult();
        for (j = 0; j < 3; j++) {
            switch (j)
            {
                case 1:
                    /*for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).R, resultList.get(i).result);

                    }*/
                    linear_regression(resultList,'R',calib);
                    break;
                case 2:
                    /*for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).G, resultList.get(i).result);

                    }*/
                    linear_regression(resultList,'G',calib);
                    break;
                case 3:
                    /*for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).B, resultList.get(i).result);

                    }*/
                    linear_regression(resultList,'B',calib);
                    break;

            }


        }

        /*******************Draw graph*******************************/
        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] dpArray = new DataPoint[totalResults];
        for (i = 0; i < totalResults; i++) {
            dpArray[i] = new DataPoint(resultList.get(i).getColor(calib.color), resultList.get(i).result);
        }
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<DataPoint>(dpArray);
        graph.addSeries(series);
        // set second scale
        /******series2*******************************/
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]  {

                new DataPoint(100,  calib.m*100 + calib.c),
                new DataPoint(500,  calib.m*500 + calib.c),
        });
        graph.addSeries(series2);
        graph.setBackgroundColor(Color.rgb(191,209,219));
        graph.setTitle("Line of Best Fit");
        graph.setTitleColor(Color.BLUE);
        series.setColor(Color.RED);
        series.setTitle("Cholestrol concentration");
        DecimalFormat df = new DecimalFormat("#.#");
        series2.setTitle("Line of best fit ("+calib.color+")-y ="+df.format(calib.m)+"x+"+df.format(calib.c));
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        graph.getViewport().setScalable(true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setBackgroundColor(Color.YELLOW);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show cholesterol for y values
                    return super.formatLabel(value, isValueX) + " mg/dL";
                }
            }
        });

    }

}

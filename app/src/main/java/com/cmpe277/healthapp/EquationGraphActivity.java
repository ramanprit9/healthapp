package com.cmpe277.healthapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

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
        resultList.add(new RGB_Result(180, 200, 110, 250));
        resultList.add(new RGB_Result(225, 170, 265, 275));
        resultList.add(new RGB_Result(206, 200, 280, 295));
        resultList.add(new RGB_Result(250, 100, 270, 245));


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
                    for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).R, resultList.get(i).result);

                    }
                    linear_regression(resultList,'R',calib);
                    break;
                case 2:
                    for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).G, resultList.get(i).result);

                    }
                    linear_regression(resultList,'G',calib);
                    break;
                case 3:
                    for (i = 0; i < totalResults; i++) {
                        new DataPoint(resultList.get(i).B, resultList.get(i).result);

                    }
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

                new DataPoint(resultList.get(0).getColor(calib.color),
                        calib.m*resultList.get(0).getColor(calib.color) + calib.c),
                new DataPoint(resultList.get(1).getColor(calib.color),
                        calib.m*resultList.get(totalResults-1).getColor(calib.color) + calib.c),
        });
        graph.addSeries(series2);
        graph.setTitle("Line of Best Fit");
        series.setColor(Color.RED);
        series.setTitle("Cholestrol concentration");
        //series2.setTitle("Line of best fit -"+"calib.color");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " mg/dL";
                }
            }
        });

    }

}

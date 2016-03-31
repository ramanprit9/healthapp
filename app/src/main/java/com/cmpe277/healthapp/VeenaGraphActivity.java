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


/**
 * Created by owner on 2/17/2016.
 **/
public class VeenaGraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        /*This shows how RGB_Result class will be used*/
        ArrayList<RGB_Result> resultList = new ArrayList<>();
        resultList.add(new RGB_Result(180, 200, 110, 250));
        resultList.add(new RGB_Result(200, 200, 200, 275));
        resultList.add(new RGB_Result(210, 206, 200, 285));
        resultList.add(new RGB_Result(220, 208, 110, 290));
        resultList.add(new RGB_Result(240, 209, 200, 300));

        int i = 0;
        int totalResults = resultList.size();
        for (i = 0; i < totalResults; i++) {
            System.out.printf("R = %d   B = %d  G = %d  result = %f\n",
                    resultList.get(i).R, resultList.get(i).G, resultList.get(i).B,
                    resultList.get(i).result);

        }
        GraphView graph = (GraphView) findViewById(R.id.graph);
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(resultList.get(0).R, resultList.get(0).result),
                new DataPoint(resultList.get(1).R, resultList.get(1).result),
                new DataPoint(resultList.get(2).R, resultList.get(2).result),
                new DataPoint(resultList.get(3).R, resultList.get(3).result),
                new DataPoint(resultList.get(4).R, resultList.get(4).result)
        });
        graph.addSeries(series);
        // set second scale
        graph.setTitle("Line of Best Fit");
        series.setColor(Color.RED);
        series.setTitle("Cholestrol concentration");
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

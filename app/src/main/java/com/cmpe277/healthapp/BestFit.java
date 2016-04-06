package com.cmpe277.healthapp;

/**
 * Created by owner on 3/31/2016.
 */

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

/******************************************************************************
 *  Compilation:  javac LinearRegression.java StdIn.java
 *  Execution:    java LinearRegression < data.txt
 *
 *  Reads in a sequence of pairs of real numbers and computes the
 *  best fit (least squares) line y  = ax + b through the set of points.
 *  Also computes the correlation coefficient and the standard error
 *  of the regression coefficients.
 *
 *  Note: the two-pass formula is preferred for stability.
 *
 ******************************************************************************/

public class BestFit extends Activity{
    public static double beta1;
    public static double beta0;
    public static void linear_regression(ArrayList<RGB_Result> args, char color, CalibResult calib) {
        //int MAXN = 100;
        int n = 0;
        //double[] x = new double[args.size()];
        //double[] y = new double[args.size()];

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        //System.out.println("length"+args.length);
        System.out.println("length"+args.size());
        System.out.println("print before while");
        // while(!StdIn.isEmpty()) {

            //int n = 0;
            while (n < args.size()) {

                //sumx += (double)args.get(n).R;
                sumx += (double)args.get(n).getColor(color);
                sumx2 += (double)args.get(n).getColor(color) * (double)args.get(n).getColor(color);
                sumy += args.get(n).result;
                n++;
            }

        System.out.println("################print after while");
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (args.get(i).getColor(color) - xbar) * (args.get(i).getColor(color) - xbar);
            yybar += (args.get(i).result) * (args.get(i).result - ybar);
            xybar += (args.get(i).getColor(color) - xbar) * (args.get(i).result - ybar);
        }
        beta1 = xybar / xxbar;
        beta0 = ybar - beta1 * xbar;

        // print results

        System.out.println("*****************************************************");
        System.out.println("y   = " + CalibrateActivity.beta1 + " * x + " + CalibrateActivity.beta0);

        System.out.println("*****************************************************");
        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1*args.get(i).getColor(color) + beta0;
            rss += (fit - args.get(i).result) * (fit - args.get(i).result);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
        System.out.println("*****************************************************");
        System.out.println("R^2                 = " + R2);
        System.out.println("*****************************************************");
        if (calib.R2<R2) {
            calib.setCalibration(color, beta1, beta0,R2);

        }


    }


}



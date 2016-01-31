/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.cmpe277.healthapp.visualization;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmpe277.healthapp.R;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

public class LineFragment extends Fragment implements AdapterView.OnItemSelectedListener{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_linegraph, container, false);

        //Spinner (drop-down menu) to show the test result options
        Spinner resultSpinner = (Spinner) v.findViewById(R.id.spinnerResults);
        String[] testReultsArray = {
                "Cholesterol",
                "Blood"
        };

        //Set adapter for the test result spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item, testReultsArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(dataAdapter);
        resultSpinner.setOnItemSelectedListener(this);

        Line l = new Line();
		LinePoint p = new LinePoint();
		p.setX(7);
		p.setY(250);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(8);
		p.setY(243);
		l.addPoint(p);
		p = new LinePoint();
		p.setX(9);
		p.setY(241);
		l.addPoint(p);
        p = new LinePoint();
        p.setX(10);
        p.setY(245);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(11);
        p.setY(247);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(12);
        p.setY(240);
        l.addPoint(p);

        l.setColor(Color.parseColor("#FFBB33"));
		
		final LineGraph li = (LineGraph)v.findViewById(R.id.linegraph);
		li.addLine(l);
		//li.setRangeY(200,400);
		li.setLineToFill(0);

		li.setOnPointClickedListener(new OnPointClickedListener(){

			@Override
			public void onClick(int lineIndex, int pointIndex) {
				// TODO Auto-generated method stub
				LinePoint p = li.getLine(lineIndex).getPoint(pointIndex);
                int xInt = Math.round(p.getX());
                int yInt = Math.round(p.getY());
                String msg = "2015-"+ xInt+"-1:   " + yInt;
                makeToast(msg);
            }
			
		});
		
		return v;
	}

    public void makeToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

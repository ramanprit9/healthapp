package com.cmpe277.healthapp.visualization;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmpe277.healthapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ramanprit Kaur on 12/6/2015.
 */

/*
 * This class shows the Past Results in a table (ListView) form
 */
public class TableFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ArrayList<TestResult> currentDataList;
    ListView result_list_vew; //to display the result in table format

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_resulttable, container, false);
        assert v != null;

        //ListView to shows the past results in a tabular format
        ListView resultTableList = (ListView)v.findViewById(R.id.listViewPastResults);

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

        //to display the result in table format
        result_list_vew = (ListView) v.findViewById(R.id.listViewPastResults);
        populateList();

        return v;
    }

    /*
     * Called when the data selection
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void populateList() {
        Data data = new Data();
        /* get the data list for the selected sensor */
        currentDataList = data.getCholesterolResultList();


        /* populate the ArrayAdapter for the Data List */
        ArrayAdapter<TestResult> adapter = new DataListAdapter();
        result_list_vew.setAdapter(adapter);
    }

    private class DataListAdapter extends ArrayAdapter<TestResult> {
        public DataListAdapter() {
            super (TableFragment.this.getActivity(), R.layout.data_list, currentDataList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getActivity().getLayoutInflater().inflate(R.layout.data_list, parent, false);
            TestResult currentResult = currentDataList.get(position);
            TextView date = (TextView) view.findViewById(R.id.txtRValue);
            date.setText((currentResult.get_date()).getYear() + "-" +
                    currentResult.get_date().getMonth() + "-" +
                    currentResult.get_date().getDate());
            TextView result = (TextView) view.findViewById(R.id.txtBValue);
            result.setText(currentResult.get_result().toString());
            return view;
        }
    }



}

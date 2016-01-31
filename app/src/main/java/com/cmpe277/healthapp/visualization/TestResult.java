package com.cmpe277.healthapp.visualization;

import java.util.Date;

/**
 * Created by Ramanprit Kaur on 12/6/2015.
 */
public class TestResult {
    Date _date;
    Double _result;

    public TestResult(Date date, Double result) {
        this._date = date;
        this._result = result;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public Double get_result() {
        return _result;
    }

    public void set_result(Double _result) {
        this._result = _result;
    }
}

package com.project.bishoy.soleektask.addplan;

import android.text.TextUtils;

/**
 * Created by bisho on 1/27/2018.
 */

public class Validator {


    public boolean validateBudget(int data) {

        if (data >= 100 && data <= 10000000) {
            return true;
        }

        return false;
    }

    public boolean validateLocation(String data) {

        if (TextUtils.isEmpty(data) || data.equals("Any")) {
            return false;
        }

        return true;
    }
}


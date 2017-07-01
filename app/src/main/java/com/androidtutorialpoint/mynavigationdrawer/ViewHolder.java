package com.androidtutorialpoint.mynavigationdrawer;

import android.widget.TextView;

/**
 * Created by Admin on 6/20/2017.
 */

public class ViewHolder {
    TextView tv_name;
    TextView tv_phoneNumber;

    public ViewHolder(){}

    public TextView getTv_name() {
        return tv_name;
    }

    public void setTv_name(TextView tv_name) {
        this.tv_name = tv_name;
    }

    public TextView getTv_phoneNumber() {
        return tv_phoneNumber;
    }

    public void setTv_phoneNumber(TextView tv_phoneNumber) {
        this.tv_phoneNumber = tv_phoneNumber;
    }
}

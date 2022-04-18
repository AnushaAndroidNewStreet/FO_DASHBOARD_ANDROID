package com.example.fodashboard.common;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.fodashboard.R;

public class ProgressBarForDataLoad {

   public ProgressDialog progressDialog = null;

    public void showProgress(Context context,String message) {

        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message +"...");
        progressDialog.setTitle("Please wait");
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDialog.show();
    }
}

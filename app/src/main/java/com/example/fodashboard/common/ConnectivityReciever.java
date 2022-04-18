package com.example.fodashboard.common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

import com.example.fodashboard.R;

import java.text.DecimalFormat;

public class ConnectivityReciever extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;


    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    public static String  format(Double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String yourFormattedString = formatter.format(amount);
        return  yourFormattedString;
    }

    public static  void errorDialogue(Context context,String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        //Creating dialog box
        builder.setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true; // Consumed
                } else {
                    return false; // Not consumed
                }
            }
        });

        AlertDialog dialog = builder.create();
        //Setting the title manually

        builder.setCancelable(false);
        dialog.setTitle(Html.fromHtml("<font color='#F74464'>Error</font>"));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
           Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
           positiveButton.setTextColor(context.getResources().getColor(R.color.colorSelectedDate));
       // dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }



}
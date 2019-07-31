package com.lambton.lofterapp.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class CommonMethods {

    public static void alertMessageOk(final Activity activity, String strTitle, String strMessage) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
        if (strTitle.length() > 0) {
            alertbox.setTitle(strTitle);
        }
        alertbox.setMessage(strMessage);
        alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertbox.show();
    }

    public static void alertMessageOkCallBack(final Activity activity, String strTitle, String strMessage) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
        if (strTitle.length() > 0) {
            alertbox.setTitle(strTitle);
        }
        alertbox.setMessage(strMessage);
        alertbox.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                activity.finish();
            }
        });
        alertbox.show();
    }
}

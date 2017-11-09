package com.wmtcore.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class BaseUtils {

    private static ProgressDialog pDialog;

    public static void showProgressDialog(Activity activity, String msg) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(msg);
        pDialog.show();
    }

    public static void ChangeProgressDialogMsg(String msg) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.setMessage(msg);
    }

    public static void showProgressDialog(Activity activity, String msg, boolean isCancel) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(msg);
        pDialog.show();
        pDialog.setCancelable(isCancel);
    }

    public static void cancelProgressDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        //snackBarView.setBackgroundColor(Color.argb(255, 8, 20, 37));
        snackbar.show();
    }

    public static void showSnackBarWithAction(View view, String msg, String actionText,
                                              View.OnClickListener onClickListener, int length) {
        Snackbar snackbar = Snackbar.make(view, msg, length)
                .setAction(actionText, onClickListener);
        View snackBarView = snackbar.getView();
        //snackBarView.setBackgroundColor(Color.argb(255, 8, 20, 37));
        snackbar.show();// Don’t forget to show!
    }
    public static int getColor(Context context, int resColor) {
        try {
            if (SupportVersion.marshmallow()) {
                return ContextCompat.getColor(context, resColor);
            } else {
                return context.getResources().getColor(resColor);
            }
        } catch (Resources.NotFoundException e) {
            return resColor;
        }
    }

    public static int parseColor(String strColor) {
        try {
            return Color.parseColor(strColor);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Color.BLACK;
    }

    public static boolean isInternetAvailable(Context context) {
        if (isInternet()) {
            return true;
        } else {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return activeNetwork.getState() == NetworkInfo.State.CONNECTED;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return activeNetwork.getState() == NetworkInfo.State.CONNECTED;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private static boolean isInternet() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
package com.wmt.android.demobaseproject.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wmt.android.demobaseproject.DemoBaseProjectApp;
import com.wmt.android.demobaseproject.R;
import com.wmt.android.demobaseproject.fragment.BaseFragment;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {

    public static void startFragment(FragmentTransaction fragmentTransaction, BaseFragment fragment,
                                     boolean withAnimation, boolean withBackStack, int fragmentContainerId) {
        if (withAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_in_left,
                    R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right,
                    R.anim.fragment_slide_out_right);
        }
        fragmentTransaction.replace(fragmentContainerId, fragment, fragment.getTagText());
        if (withBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTagText());
        }
        fragmentTransaction.commit();
    }

    public static void startFragment(FragmentTransaction fragmentTransaction, BaseFragment fragment,
                                     boolean withAnimation, String tag, int fragmentContainerId) {
        if (withAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_in_left,
                    R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right,
                    R.anim.fragment_slide_out_right);
        }
        fragmentTransaction.replace(fragmentContainerId, fragment, fragment.getTagText());
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public static void popBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate();
    }

    public static void popBackStackTillTag(FragmentManager fragmentManager, String tag) {
        fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static boolean isValidEmail(CharSequence strEmail) {
        return !TextUtils.isEmpty(strEmail)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail)
                .matches();
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
    
    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String appKeyHash() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        String keyHash = null;
        PackageInfo info = DemoBaseProjectApp.getAppInstance().getPackageManager().getPackageInfo(
                "com.wmt.android.demobaseproject",
                PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
        }
        return keyHash;
    }
}

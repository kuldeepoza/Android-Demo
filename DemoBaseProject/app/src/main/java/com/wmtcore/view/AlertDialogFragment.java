package com.wmtcore.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.wmt.android.demobaseproject.R;
import com.wmtcore.util.BaseUtils;

public class AlertDialogFragment extends DialogFragment implements View.OnClickListener {

    AlertDialog dialog;
    String callbackMsg = "Missing Callback";
    private OnAlertDialogClickListener mListener;
    private String positiveText;
    private String negativeText;
    private String neutralText;
    private String title;
    private String message;
    private boolean cancelable;
    private int positiveColor = Color.BLACK;
    private int negativeColor = Color.BLACK;
    private int neutralColor = Color.BLACK;
    private int icon;
    private int dialogId;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle sis) {

        ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (TextUtils.isEmpty(positiveText)) {
            positiveText = "OK";
        }
        builder.setPositiveButton(positiveText, null);
        builder.setNegativeButton(negativeText, null);
        builder.setNeutralButton(neutralText, null);
        builder.setCancelable(cancelable);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(icon);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = (AlertDialog) getDialog();

        Button posButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setTag(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(this);
        posButton.setTextColor(positiveColor);

        Button negButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        negButton.setTag(Dialog.BUTTON_NEGATIVE);
        negButton.setOnClickListener(this);
        negButton.setTextColor(negativeColor);

        Button neuButton = dialog.getButton(Dialog.BUTTON_NEUTRAL);
        neuButton.setTag(Dialog.BUTTON_NEUTRAL);
        neuButton.setOnClickListener(this);
        negButton.setTextColor(neutralColor);
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case Dialog.BUTTON_POSITIVE:
                if (mListener == null)
                    BaseUtils.showToast(getActivity(), callbackMsg);
                else
                    mListener.onPositiveClick(dialog, dialogId);
                break;

            case Dialog.BUTTON_NEGATIVE:
                if (mListener == null)
                    BaseUtils.showToast(getActivity(), callbackMsg);
                else
                    mListener.onNegativeClick(dialog, dialogId);
                break;

            case Dialog.BUTTON_NEUTRAL:
                if (mListener == null)
                    BaseUtils.showToast(getActivity(), callbackMsg);
                else
                    mListener.onNeutralClick(dialog, dialogId);
                break;
        }
    }

    public interface OnAlertDialogClickListener {
        void onPositiveClick(DialogInterface dialog, int id);

        void onNegativeClick(DialogInterface dialog, int id);

        void onNeutralClick(DialogInterface dialog, int id);
    }

    public static class Builder {

        AlertDialogFragment alertDialogFragment;

        public Builder() {
            alertDialogFragment = new AlertDialogFragment();
        }

        public Builder setPositiveButtonText(String positiveText) {
            alertDialogFragment.positiveText = positiveText;
            return this;
        }

        public Builder setNegativeButtonText(String negativeText) {
            alertDialogFragment.negativeText = negativeText;
            return this;
        }

        public Builder setNeutralButtonText(String neutralButtonText) {
            alertDialogFragment.neutralText = neutralButtonText;
            return this;
        }

        public Builder setTitle(String title) {
            alertDialogFragment.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            alertDialogFragment.message = message;
            return this;
        }

        public Builder setDialogId(int dialogId) {
            alertDialogFragment.dialogId = dialogId;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            alertDialogFragment.cancelable = cancelable;
            return this;
        }

        public Builder setTextColorPositive(String strColor) {
            alertDialogFragment.positiveColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorPositive(int color) {
            alertDialogFragment.positiveColor = BaseUtils.getColor(alertDialogFragment.getActivity(), color);
            return this;
        }

        public Builder setTextColorNegative(String strColor) {
            alertDialogFragment.negativeColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorNegative(int color) {
            alertDialogFragment.negativeColor = BaseUtils.getColor(alertDialogFragment.getActivity(), color);
            return this;
        }

        public Builder setTextColorNeutral(String strColor) {
            alertDialogFragment.neutralColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorNeutral(int color) {
            alertDialogFragment.neutralColor = BaseUtils.getColor(alertDialogFragment.getActivity(), color);
            return this;
        }

        public Builder setCallback(OnAlertDialogClickListener mListener) {
            alertDialogFragment.mListener = mListener;
            return this;
        }

        public AlertDialogFragment build() {
            return alertDialogFragment;
        }
    }

}
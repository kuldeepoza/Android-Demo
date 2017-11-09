package com.wmtcore.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wmt.android.demobaseproject.R;
import com.wmtcore.util.BaseUtils;

import java.util.Locale;

public class InputDialogFragment extends DialogFragment implements View.OnClickListener {

    String callbackMsg = "Missing Callback";
    TextView tvCharacterCount;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length

            if (s.length() > 140) {
                tvCharacterCount.setTextColor(BaseUtils.getColor(getActivity(), R.color.colorPrimary));
            } else {
                tvCharacterCount.setTextColor(BaseUtils.getColor(getActivity(), R.color.colorPrimaryDark));
            }
            tvCharacterCount.setText(String.format(Locale.getDefault(), "%d", (140 - s.length())));
        }

        public void afterTextChanged(Editable s) {
            if (s.length() > 140) {
                s.setSpan(new ForegroundColorSpan(BaseUtils.getColor(getActivity(), R.color.colorPrimary)), 140, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
    };
    private EditText etMessage;
    private String errorMessage;
    private String hintMessage;
    private AlertDialog dialog;
    private String posText;
    private String negText;
    private String neutralText;
    private String title;
    private boolean cancelable;
    private int posColor = Color.BLACK;
    private int negColor = Color.BLACK;
    private int neutralColor = Color.BLACK;
    private int icon;
    private OnInputDialogClickListener alertListener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_dialog_input, null);

        etMessage = (EditText) view.findViewById(R.id.etMessage);
        tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);
        etMessage.setInputType(InputType.TYPE_CLASS_TEXT);
        etMessage.setSelection(etMessage.getText().length());
        etMessage.addTextChangedListener(mTextEditorWatcher);
        etMessage.setMovementMethod(new ScrollingMovementMethod());

        if (!TextUtils.isEmpty(this.hintMessage))
            etMessage.setHint(this.hintMessage);

        if (TextUtils.isEmpty(this.posText))
            this.posText = "OK";
        tvCharacterCount.setText(String.format(Locale.getDefault(), "%d", (140 - etMessage.getText().length())));
        builder.setPositiveButton(this.posText, null);
        builder.setNegativeButton(this.negText, null);
        builder.setNeutralButton(this.neutralText, null);
        builder.setIcon(this.icon);
        builder.setCancelable(this.cancelable);
        builder.setView(view);
        builder.setTitle(this.title);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = (AlertDialog) getDialog();

        Button posButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setTag(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(this);
        posButton.setTextColor(this.posColor);

        Button negButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        negButton.setTag(Dialog.BUTTON_NEGATIVE);
        negButton.setOnClickListener(this);
        negButton.setTextColor(this.negColor);

        Button neuButton = dialog.getButton(Dialog.BUTTON_NEUTRAL);
        neuButton.setTag(Dialog.BUTTON_NEUTRAL);
        negButton.setTextColor(this.neutralColor);
        neuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case Dialog.BUTTON_POSITIVE:
                if (!TextUtils.isEmpty(errorMessage)) {
                    if (TextUtils.isEmpty(etMessage.getText().toString())) {
                        this.etMessage.setError(errorMessage);
                    } else {
                        if (alertListener == null) {
                            BaseUtils.showToast(getActivity(), callbackMsg);
                        } else {
                            String msg = etMessage.getText().toString();
                            msg = msg.length() > 140 ? msg.substring(0, 139) : msg;
                            alertListener.onMessagePositiveClick(dialog, msg);
                        }
                    }
                } else {
                    if (alertListener == null)
                        BaseUtils.showToast(getActivity(), callbackMsg);
                    else
                        alertListener.onMessagePositiveClick(dialog, etMessage.getText().toString());
                }
                break;

            case Dialog.BUTTON_NEGATIVE:
                if (alertListener == null)
                    BaseUtils.showToast(getActivity(), callbackMsg);
                else
                    alertListener.onMessageNegativeClick(dialog);
                break;

            case Dialog.BUTTON_NEUTRAL:
                if (alertListener == null)
                    BaseUtils.showToast(getActivity(), callbackMsg);
                else
                    alertListener.onMessageNeutralClick(dialog);
                break;
        }
    }

    public interface OnInputDialogClickListener {
        void onMessagePositiveClick(DialogInterface dialog,
                                    String cancelMessage);

        void onMessageNegativeClick(DialogInterface dialog);

        void onMessageNeutralClick(DialogInterface dialog);
    }

    public static class Builder {

        InputDialogFragment dialogFragment;

        public Builder() {
            dialogFragment = new InputDialogFragment();
        }

        public Builder setTitle(String title) {
            dialogFragment.title = title;
            return this;
        }

        public Builder setNegativeButtonText(String negativeButtonText) {
            dialogFragment.negText = negativeButtonText;
            return this;
        }

        public Builder setPositiveButtonText(String positiveButtonText) {
            dialogFragment.posText = positiveButtonText;
            return this;
        }

        public Builder setNeutralButtonText(String neutralButtonText) {
            dialogFragment.neutralText = neutralButtonText;
            return this;
        }

        public Builder setIcon(int icon) {
            dialogFragment.icon = icon;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            dialogFragment.errorMessage = errorMessage;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            dialogFragment.cancelable = cancelable;
            return this;
        }

        public Builder setAlertListener(OnInputDialogClickListener mListener) {
            dialogFragment.alertListener = mListener;
            return this;
        }

        public Builder setHintText(String hintText) {
            dialogFragment.hintMessage = hintText;
            return this;
        }

        public Builder setTextColorPositive(String strColor) {
            dialogFragment.posColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorPositive(int color) {
            dialogFragment.posColor = BaseUtils.getColor(dialogFragment.getActivity(), color);
            return this;
        }

        public Builder setTextColorNegative(String strColor) {
            dialogFragment.negColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorNegative(int color) {
            dialogFragment.negColor = BaseUtils.getColor(dialogFragment.getActivity(), color);
            return this;
        }

        public Builder setTextColorNeutral(String strColor) {
            dialogFragment.neutralColor = BaseUtils.parseColor(strColor);
            return this;
        }

        public Builder setTextColorNeutral(int color) {
            dialogFragment.neutralColor = BaseUtils.getColor(dialogFragment.getActivity(), color);
            return this;
        }

        public InputDialogFragment build() {
            return dialogFragment;
        }
    }

}
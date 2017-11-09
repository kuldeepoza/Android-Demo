package com.wmt.android.demobaseproject.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.wmt.android.demobaseproject.R;
import com.wmtcore.util.SupportVersion;
import com.wmtcore.view.AlertDialogFragment;

public abstract class BaseSinglePermissionFragment extends BaseFragment implements PermissionListener,
        PermissionRequestErrorListener,
        AlertDialogFragment.OnAlertDialogClickListener {

    public static final String TAG = BaseSinglePermissionFragment.class.getSimpleName();

    private PermissionListener permissionListener;
    private ViewGroup viewGroup;
    private PermissionToken permissionToken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = container;
        createPermissionListeners();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void createPermissionListeners() {
        permissionListener = new CompositePermissionListener(this,
                SnackbarOnDeniedPermissionListener.Builder.with(viewGroup,
                        R.string.permission_denied_feedback)
                        .withOpenSettingsButton(R.string.btn_settings)
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                            }
                        })
                        .build());
    }

    protected void checkPermission() {
        if (SupportVersion.marshmallow())
            Dexter.withActivity(getActivity())
                    .withPermission(permission())
                    .withListener(permissionListener)
                    .withErrorListener(this)
                    .check();
        else
            permissionGiven();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        permissionGiven();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        permissionDeny();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        this.permissionToken = token;
        new AlertDialogFragment.Builder()
                .setTitle(permissionRationalTitle())
                .setMessage(permissionRationalDesc())
                .setCancelable(false)
                .setPositiveButtonText(getString(R.string.btn_retry))
                .setNegativeButtonText(getString(R.string.btn_imsure))
                .setCallback(this)
                .setDialogId(getPermissionDialogId())
                .build()
                .show(getFragmentManager(), TAG);
    }

    @Override
    public void onError(DexterError error) {
    }

    @Override
    public void onPositiveClick(DialogInterface dialog, int id) {
        if (id == getPermissionDialogId()) {
            permissionToken.continuePermissionRequest();
            dialog.dismiss();
        }
    }

    @Override
    public void onNegativeClick(DialogInterface dialog, int id) {
        if (id == getPermissionDialogId()) {
            permissionToken.cancelPermissionRequest();
            dialog.dismiss();
        }
    }

    @Override
    public void onNeutralClick(DialogInterface dialog, int id) {
        if (id == getPermissionDialogId()) {
            permissionToken.cancelPermissionRequest();
            dialog.dismiss();
        }
    }

    public abstract void permissionGiven();

    public abstract void permissionDeny();

    public abstract int getPermissionDialogId();

    public abstract String permission();
    
    public abstract String permissionRationalTitle();

    public abstract String permissionRationalDesc();
}
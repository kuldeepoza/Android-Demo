package com.wmt.android.demobaseproject.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.wmt.android.demobaseproject.R;
import com.wmtcore.util.SupportVersion;
import com.wmtcore.view.AlertDialogFragment;

import java.util.List;

public abstract class BaseMultiPermissionFragment extends BaseFragment implements MultiplePermissionsListener,
        PermissionRequestErrorListener,
        AlertDialogFragment.OnAlertDialogClickListener {

    public static final String TAG = BaseMultiPermissionFragment.class.getSimpleName();

    private MultiplePermissionsListener multiplePermissionsListener;
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
        multiplePermissionsListener = new CompositeMultiplePermissionsListener(this,
                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(viewGroup,
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
                    .withPermissions(permissions())
                    .withListener(multiplePermissionsListener)
                    .withErrorListener(this)
                    .check();
        else
            permissionGiven();

    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        /*for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            showPermissionGranted(response.getPermissionName());
        }

        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
            showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
        }*/
        if (report.getDeniedPermissionResponses().size() > 0) permissionDeny();
        else permissionGiven();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        permissionToken = token;
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

    public void showPermissionGranted(String permission) {
    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
    }

    public abstract void permissionGiven();

    public abstract void permissionDeny();

    public abstract int getPermissionDialogId();

    public abstract String[] permissions();

    public abstract String permissionRationalTitle();

    public abstract String permissionRationalDesc();
}
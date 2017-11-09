package com.wmt.android.demobaseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wmt.android.demobaseproject.R;
import com.wmt.android.demobaseproject.activity.BaseActivity;
import com.wmt.android.demobaseproject.adapter.MainAdapter;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    protected BaseActivity baseActivity;
    protected Bundle fragmentBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getFragmentLayoutId(), container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivityHandling();
        activityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    private void initActivityHandling() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.setBackIcon(isBackIcon(), isDrawerIcon());
        baseActivity.setSelectedFragment(getFragment());
        if (getToolbarTitle() != null)
            baseActivity.setTitle(getToolbarTitle());
    }

    public Bundle getFragmentBundle() {
        return fragmentBundle;
    }

    public void isBackupIcon(boolean isBackupIcon,boolean isSetDrawerIcon) {
        baseActivity.setBackIcon(isBackupIcon,isSetDrawerIcon);
    }

    public void setToolbarTitle(String toolbarTitle) {
        baseActivity.setTitle(getToolbarTitle());
    }

    public abstract boolean isDrawerIcon();

    public abstract void bindView(View view);

    public abstract void activityCreated(Bundle savedInstanceState);

    public abstract String getTagText();

    public abstract boolean onBackPressed();

    public abstract int getFragmentLayoutId();

    public abstract String getToolbarTitle();

    public abstract boolean isBackIcon();

    public abstract BaseFragment getFragment();
}

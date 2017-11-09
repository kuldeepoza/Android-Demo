package com.wmt.android.demobaseproject.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.wmt.android.demobaseproject.R;
import com.wmt.android.demobaseproject.adapter.MainAdapter;

import java.util.ArrayList;

public class MainFragment extends BaseFragment  {
    MainAdapter adapter;
    RecyclerView rv;
    ArrayList list;
    public static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public String getTagText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.toolbar_title_fragment);
    }

    @Override
    public boolean isBackIcon() {
        return true;
    }

 @Override
    public boolean isDrawerIcon() {
        return false;
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void bindView(View view) {
        rv=(RecyclerView)view.findViewById(R.id.RecyclerView);

    }

    @Override
    public void activityCreated(Bundle savedInstanceState) {
        list=new ArrayList();
        list.add("kuldeep");
        list.add("Raj");
        list.add("Vishal");
        list.add("Mukesh");
        list.add("Niraj");
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new MainAdapter(getActivity(),list);
        rv.setAdapter(adapter);
    }
}

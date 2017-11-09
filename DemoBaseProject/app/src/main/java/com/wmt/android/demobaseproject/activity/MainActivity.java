package com.wmt.android.demobaseproject.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wmt.android.demobaseproject.R;
import com.wmt.android.demobaseproject.fragment.MainFragment;
import com.wmt.android.demobaseproject.util.CommonUtils;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.startFragment(getSupportFragmentManager().beginTransaction(), new MainFragment(),
                false, false, R.id.fragment_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getToolbarId() {
        return R.id.common_toolbar;
    }
}
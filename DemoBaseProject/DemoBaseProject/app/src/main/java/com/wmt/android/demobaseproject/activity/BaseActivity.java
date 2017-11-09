package com.wmt.android.demobaseproject.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wmt.android.demobaseproject.R;
import com.wmt.android.demobaseproject.fragment.BaseFragment;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BaseFragment selectedFragment;
  private boolean isBackIcon;
    private boolean isDrawerIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (getToolbarId() > 0) {
            toolbar = (Toolbar) findViewById(getToolbarId());
            setSupportActionBar(toolbar);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setTitle(String toolbarTitle) {
        if (getToolbarTextViewId() != 0) {
            TextView tvTitle = (TextView) toolbar.findViewById(getToolbarTextViewId());
            tvTitle.setText(toolbarTitle);
            return;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            super.setTitle(toolbarTitle);
        } else {
            actionBar.setTitle(toolbarTitle);
        }
    }

    public void setBackIcon(boolean isSetBackIcon,boolean isSetDrawerIcon) {
        isBackIcon = isSetBackIcon;
        isDrawerIcon = isSetDrawerIcon;
        if (getToolbar() == null) return;

        if (isSetBackIcon) {
            getToolbar().setNavigationIcon(R.drawable.ic_action_nav_back);
        } 
        else if (isSetDrawerIcon) {
            getToolbar().setNavigationIcon(R.drawable.ic_dehaze_white_24dp);
        }else {
            getToolbar().setNavigationIcon(null);
        }
    }

    public void hideToolbar() {
        if (getToolbar() == null) return;
        toolbar.setVisibility(View.GONE);
    }

    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isBackIcon)
                    onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == null) {
            finish();
            return;
        }

        if (!selectedFragment.onBackPressed()) {
            finish();
            overridePendingTransition(R.anim.fragment_slide_in_right,
                    R.anim.fragment_slide_out_right);
        }
    }

    public abstract int getLayoutId();

    public abstract int getToolbarId();

    protected int getToolbarTextViewId() {
        return 0;
    }
}

package com.wmt.android.demobaseproject.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wmt.android.demobaseproject.R;

/**
 * Created by KD on 08-Nov-17.
 */

public class RegistrationActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    EditText e1,e2,e3,e4,e5;
    Button b1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e5=(EditText)findViewById(R.id.editText5);
        b1=(Button)findViewById(R.id.button);
        setOnClick();

    }

    private void setOnClick() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
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
        return R.layout.activity_registration;
    }

    @Override
    public int getToolbarId() {
        return R.layout.common_toolbar;
    }
}

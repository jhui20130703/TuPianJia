package com.icon.image.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.icon.image.R;
import com.icon.image.activity.base.BaseActivity;

public class FraWearthActivity extends BaseActivity {

    @Override
    protected int intiLayout() {
        return R.layout.activity_wearther;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBar(true);
        setTitle(R.string.action_city);
    }

}
package com.icon.image.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.icon.image.R;
import com.lzy.okgo.OkGo;

public abstract class BaseActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private FrameLayout mContentLayout;
    private ProgressBar mProgressBar;
    private ConstraintLayout mCoordinatorLayout;

    /**
     * 设置布局
     */
    protected abstract int intiLayout();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getDelegate().setContentView(R.layout.activity_base);
        findRootView();
        setContentView(intiLayout());
        initOnCreate();
        initView();
        initEvent();
        setBackBar(true);
        setSupportActionBar(mToolbar);
    }

    protected void initOnCreate(){

    }

    private void findRootView() {
        mToolbar = findViewById(R.id.toolbar);
        mContentLayout = findViewById(R.id.content);
        mProgressBar = findViewById(R.id.progressBar);
        mCoordinatorLayout = findViewById(R.id.coordinatorlayout);
    }

    public ConstraintLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    public FrameLayout getContentLayout() {
        return mContentLayout;
    }

    public Toolbar getToolBar(){
        return mToolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mContentLayout, true);
    }

    protected void setBackBar(boolean isShow) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    /**
     * 显示 ProgressBar
     */
    public void showProgresBar(){
        if (mProgressBar != null && mProgressBar.getVisibility() == View.GONE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏 ProgressBar
     */
    public void hiddenProgresBar(){
        if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}

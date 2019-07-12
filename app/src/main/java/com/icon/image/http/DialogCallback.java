package com.icon.image.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.icon.image.activity.base.BaseActivity;
import com.lzy.okgo.request.base.Request;

@SuppressWarnings("all")
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private BaseActivity baseActivity;

    protected DialogCallback(Activity activity) {
        super();
        baseActivity = (BaseActivity) activity;
    }

    /**
     * 网络请求开始打开对话框
     */
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (baseActivity != null) {
            baseActivity.showProgresBar();
        }
    }

    /**
     * 网络请求结束后关闭对话框
     */
    @Override
    public void onFinish() {
        if (baseActivity != null) {
            baseActivity.hiddenProgresBar();
        }
    }
}

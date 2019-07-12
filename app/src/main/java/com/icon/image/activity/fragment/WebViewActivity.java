package com.icon.image.activity.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.icon.image.R;
import com.icon.image.activity.base.BaseActivity;
import com.just.agentweb.AgentWeb;

public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private String url;

    @Override
    protected int intiLayout() {
        return R.layout.activity_search_express;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void initEvent() {
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getContentLayout(), new ConstraintLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        webView = mAgentWeb.getWebCreator().getWebView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        setBackBar(true);
        setTitle(TextUtils.equals(type, "EXPRESS") ? R.string.action_search : R.string.dialog_github);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null && webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

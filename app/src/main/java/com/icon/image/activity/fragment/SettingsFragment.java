package com.icon.image.activity.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.icon.image.R;
import com.icon.image.activity.FraWearthActivity;
import com.icon.image.activity.MainActivity;
import com.icon.image.activity.base.BaseFragment;
import com.icon.image.utils.GlideLoader;
import com.icon.image.utils.ImagePicker;
import com.icon.image.view.MyOneLineView;

/**
 * 设置
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private MainActivity activity;
    private MyOneLineView mov_wearther;
    private MyOneLineView mov_mediaplay;
    private MyOneLineView mov_search;
    private RelativeLayout rl_item_head;

    public SettingsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
    }

    @Override
    protected int getCreateView() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_setting);
        mov_wearther = view.findViewById(R.id.mov_wearther);
        mov_mediaplay = view.findViewById(R.id.mov_mediaplay);
        mov_search = view.findViewById(R.id.mov_search);
        rl_item_head = view.findViewById(R.id.rl_item_head);
    }

    @Override
    protected void initData() {
        mov_wearther.setOnClickListener(this);
        mov_mediaplay.setOnClickListener(this);
        mov_search.setOnClickListener(this);
        rl_item_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mov_mediaplay:
                ImagePicker.getInstance()
                        .setTitle("标题")//设置标题
                        .showCamera(false)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(9)//设置最大选择图片数目(默认为1，单选)
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(activity);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                break;
            case R.id.mov_wearther:
                startActivity(new Intent(activity, FraWearthActivity.class));
                break;
            case R.id.mov_search:
                View view = View.inflate(activity, R.layout.input_dialog, null);
                TextInputEditText txtExpress = view.findViewById(R.id.txt_express);
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle(R.string.dialog_tip)
                        .setView(view)
                        .setCancelable(false)
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setPositiveButton(R.string.dialog_confirm, null).show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {

                    if (TextUtils.isEmpty(txtExpress.getText())) {
                        txtExpress.requestFocus();
                        txtExpress.setError("快递运单号不能为空");
                        return;
                    }
                    dialog.dismiss();
                    Intent intent = new Intent(activity, WebViewActivity.class);
                    intent.putExtra("type", "EXPRESS");
                    intent.putExtra("url", String.format("https://m.kuaidi100.com/result.jsp?nu=%s", txtExpress.getText()));
                    startActivity(intent);
                });
                break;
            case R.id.rl_item_head:
                Intent intent = new Intent(activity, WebViewActivity.class);
                intent.putExtra("type", "GitHub");
                intent.putExtra("url", "https://github.com/jhui20130703");
                startActivity(intent);
                break;
        }
    }
}

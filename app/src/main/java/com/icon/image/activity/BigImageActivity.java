package com.icon.image.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.icon.image.R;
import com.icon.image.activity.base.BaseActivity;
import com.icon.image.utils.BitmapUtil;
import com.icon.image.utils.FileUtil;
import com.icon.image.view.PinchImageView;

public class BigImageActivity extends BaseActivity {

    private String url;
    private PinchImageView imageView;

    @Override
    protected int intiLayout() {
        return R.layout.activity_big_image;
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.img);
    }

    @Override
    protected void initEvent() {
        url = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.ic_placeholder)).into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBar(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareGirl();
            return true;
        } else if (id == R.id.action_save) {
            saveGirl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享文字内容
     */
    public void shareGirl() {

        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_home));//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, url);//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "选择分享方式");
        startActivity(share_intent);
    }

    /**
     * 保存图片
     */
    public void saveGirl() {
        showProgresBar();
        boolean isFlag = false;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            Bitmap bitmap = BitmapUtil.drawableToBitmap(drawable);
            isFlag = BitmapUtil.saveBitmap(bitmap, FileUtil.getDiskCacheDir(this) + "/girls", String.format("%s.jpg", System.currentTimeMillis()), false);
        }
        hiddenProgresBar();
        Snackbar.make(getCoordinatorLayout(), isFlag ? "恭喜，美图保存成功！" : "错误，美图保存失败！", Snackbar.LENGTH_SHORT).show();
    }

}

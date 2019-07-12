package com.icon.image.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icon.image.R;
import com.icon.image.entity.GirlsBean;

/**
 * @author :admin
 * @date :2019-07-09
 * @desc : 图片适配器
 */
public class ImageNetAdapter extends BaseQuickAdapter<GirlsBean.ResultsEntity, BaseViewHolder> {

    private Context context;

    public ImageNetAdapter(Context context) {
        super(R.layout.item_image);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlsBean.ResultsEntity item) {
        ImageView imageView = helper.getView(R.id.imageView);
        Glide.with(context).load(item.getUrl()).apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.ic_placeholder)).into(imageView);
    }
}

package com.icon.image.activity.fragment;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icon.image.R;
import com.icon.image.activity.BigImageActivity;
import com.icon.image.activity.base.BaseActivity;
import com.icon.image.activity.base.BaseFragment;
import com.icon.image.adapter.ImageNetAdapter;
import com.icon.image.entity.GirlsBean;
import com.icon.image.http.DialogCallback;
import com.icon.image.http.JsonCallback;
import com.icon.image.http.config.HttpApi;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageNetAdapter imageNetAdapter;
    private int mNextRequestPage = 5;
    private int PAGE_SIZE = 10;
    private View emptyView;

    public LocalFragment() {

    }

    @Override
    protected int getCreateView() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected void initViews(View view) {

        mSwipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout);
        RecyclerView mRecyclerView = view.findViewById(R.id.mRecyclerView);

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorAccent));

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        imageNetAdapter = new ImageNetAdapter(activity);

        imageNetAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);

        imageNetAdapter.setOnItemClickListener(this);

        imageNetAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        mRecyclerView.setAdapter(imageNetAdapter);

        emptyView = LayoutInflater.from(activity).inflate(R.layout.no_more_layout, null);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mNextRequestPage = 5;
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        imageNetAdapter.setEnableLoadMore(false);
        OkGo.<GirlsBean>get(HttpApi.getInstance().getImageApi("福利", mNextRequestPage, PAGE_SIZE))
                .tag(this)
                .execute(new DialogCallback<GirlsBean>(activity) {
                    @Override
                    public void onSuccess(Response<GirlsBean> response) {
                        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);
                        setData(true, response.body().getResults());
                        imageNetAdapter.setEnableLoadMore(true);
                        imageNetAdapter.setEmptyView(emptyView);
                    }

                    @Override
                    public void onError(Response<GirlsBean> response) {
                        super.onError(response);
                        imageNetAdapter.setEnableLoadMore(true);
                        Snackbar.make(((BaseActivity) activity).getCoordinatorLayout(), "加载失败：" + response.message(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 上拉加载更多
     */
    private void loadMore() {
        OkGo.<GirlsBean>get(HttpApi.getInstance().getImageApi("福利", mNextRequestPage, PAGE_SIZE))
                .tag(this)
                .execute(new JsonCallback<GirlsBean>() {
                    @Override
                    public void onSuccess(Response<GirlsBean> response) {
                        setData(mNextRequestPage == 1, response.body().getResults());
                    }

                    @Override
                    public void onError(Response<GirlsBean> response) {
                        super.onError(response);
                        imageNetAdapter.loadMoreFail();
                    }
                });
    }

    private void setData(boolean isRefresh, List<GirlsBean.ResultsEntity> data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            imageNetAdapter.setNewData(data);
        } else {
            if (size > 0) {
                imageNetAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            imageNetAdapter.loadMoreEnd(isRefresh);
        } else {
            imageNetAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GirlsBean.ResultsEntity bean = imageNetAdapter.getData().get(position);
        Intent intent = new Intent(activity, BigImageActivity.class);
        intent.putExtra("imgUrl", bean.getUrl());
        activity.startActivity(intent);
    }


}

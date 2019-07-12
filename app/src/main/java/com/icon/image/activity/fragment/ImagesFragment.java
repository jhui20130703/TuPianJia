package com.icon.image.activity.fragment;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.icon.image.R;
import com.icon.image.activity.MainActivity;
import com.icon.image.activity.base.BaseFragment;
import com.icon.image.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 图片
 */
public class ImagesFragment extends BaseFragment {

    private MainActivity activity;
    private TabLayout tabLayout;
    private MyViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new OnLineFragment());
        add(new LocalFragment());
    }};

    public ImagesFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((MainActivity) context);
    }

    @Override
    protected int getCreateView() {
        return R.layout.fragment_images;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabs);
        mViewPager = view.findViewById(R.id.vp_container);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_home);
    }

    @Override
    protected void initData() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(activity.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.text_online);
                case 1:
                    return getString(R.string.text_local);
            }
            return null;
        }
    }
}

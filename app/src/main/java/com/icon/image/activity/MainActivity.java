package com.icon.image.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.icon.image.R;
import com.icon.image.activity.base.BaseActivity;
import com.icon.image.activity.fragment.ImagesFragment;
import com.icon.image.activity.fragment.SettingsFragment;
import com.lcw.library.imagepicker.manager.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private BottomNavigationView navView;
    private ViewPager vpBanner;
    private List<Fragment> fragmentList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpBanner.setCurrentItem(0);
                    return true;
                case R.id.navigation_notifications:
                    vpBanner.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    private FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    };

    @Override
    protected int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        navView = findViewById(R.id.nav_view);
        vpBanner = findViewById(R.id.vp_banner);
    }

    @Override
    protected void initEvent() {
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        vpBanner.addOnPageChangeListener(this);
        vpBanner.setAdapter(pagerAdapter);
    }

    @Override
    protected void initOnCreate() {
        super.initOnCreate();
        fragmentList.add(new ImagesFragment());
        fragmentList.add(new SettingsFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setBackBar(false);
        getToolBar().setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        //让与当前Pager相应的item变为选中状态
        MenuItem menuItem = navView.getMenu().getItem(i);
        menuItem.setChecked(true);
        getToolBar().setTitle(menuItem.getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Snackbar.make(getCoordinatorLayout(), "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

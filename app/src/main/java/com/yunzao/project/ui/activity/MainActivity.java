package com.yunzao.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ike.l2_base.base.ToolBarActivity;
import com.ike.l2_base.glide.CropCircleTransformation;
import com.yunzao.project.R;
import com.yunzao.project.ui.adapter.FragmentPageAdapter;
import com.yunzao.project.ui.fragment.MainPageFragment;
import com.yunzao.project.ui.widget.NoScrollViewPager;
import com.yunzao.project.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolBarActivity {
    private static String TAG = MainActivity.class.getName();
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentPageAdapter fragmentPageAdapter;
    private ArrayList<MainPageFragment> fragmentArray;
    private String[] titleArray = {"Android", "IOS", "福利", "前端", "休息视频", "拓展资源"};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    ImageView myAvatar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        View headerLayout = navView.inflateHeaderView(R.layout.headlayout);
        myAvatar = ButterKnife.findById(headerLayout, R.id.myAvater);

        initView();
    }

    private void initView() {
        Glide.with(this)
                .load("https://avatars3.githubusercontent.com/u/19199353?s=40026%u=78ad14972326294fba1a1c42fa7303fe4f6b249b26%v=4")
                .bitmapTransform(new CropCircleTransformation(this))
                .into(myAvatar);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra(Constants.TITLE_H5, "关于我");
                        intent.putExtra(Constants.URL_H5, "https://github.com/dyl169");
                        startActivity(intent);
                        break;
                }

                //关闭抽屉
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle); //设置侧滑监听


        fragmentArray = new ArrayList<>();
        for (String title : titleArray) {
            fragmentArray.add(MainPageFragment.newInstance().setTitle(title));
        }

        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), titleArray, fragmentArray);
        viewPager.setAdapter(fragmentPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentPageAdapter);
    }

    public FragmentPageAdapter getFragmentPageAdapter() {
        return fragmentPageAdapter;
    }

}

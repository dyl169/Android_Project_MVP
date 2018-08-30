package com.yunzao.project.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.ike.l2_base.base.ToolBarActivity;
import com.yunzao.project.R;
import com.yunzao.project.ui.adapter.FragmentPageAdapter;
import com.yunzao.project.ui.fragment.MainPageFragment;
import com.yunzao.project.ui.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolBarActivity {
    private static String TAG = MainActivity.class.getName();
    private FragmentPageAdapter fragmentPageAdapter;
    private ArrayList<String> titleArray;
    private ArrayList<MainPageFragment> fragmentArray;

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
    }

    private void initViewPager() {
        titleArray = new ArrayList<>();
        titleArray.add("Android");
        titleArray.add("IOS");
        titleArray.add("福利");
        titleArray.add("前端");

        fragmentArray = new ArrayList<>();
        fragmentArray.add(MainPageFragment.newInstance().setTitle("Android"));
        fragmentArray.add(MainPageFragment.newInstance().setTitle("IOS"));
        fragmentArray.add(MainPageFragment.newInstance().setTitle("福利"));
        fragmentArray.add(MainPageFragment.newInstance().setTitle("前端"));

        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), titleArray, fragmentArray);
        viewPager.setAdapter(fragmentPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentPageAdapter);
    }

    public FragmentPageAdapter getFragmentPageAdapter() {
        return fragmentPageAdapter;
    }

}

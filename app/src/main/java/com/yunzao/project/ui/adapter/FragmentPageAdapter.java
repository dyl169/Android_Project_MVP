package com.yunzao.project.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunzao.project.ui.fragment.MainPageFragment;

import java.util.ArrayList;

/**
 * @auth ike
 * @date 2018/8/30
 * @desc 类描述：
 */

public class FragmentPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titleList;
    private ArrayList<MainPageFragment> fragmentList;
    private FragmentManager fragmentManager;

    public FragmentPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<MainPageFragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
        this.fragmentManager = fm;
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
        return titleList.get(position);
    }
}

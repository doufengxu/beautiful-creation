package com.example.zuimeichuangyi.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 * 广告 (所有)
 *
 * 主页ViewPager自定义适配器
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list = new ArrayList<Fragment>();

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}

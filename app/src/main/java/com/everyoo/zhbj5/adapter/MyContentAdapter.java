package com.everyoo.zhbj5.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.everyoo.zhbj5.base.BasePager;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/18.
 */
public class MyContentAdapter extends PagerAdapter {

    private ArrayList<BasePager> basePagersList;

    public MyContentAdapter(ArrayList<BasePager> basePagersList) {
        this.basePagersList = basePagersList;
    }


    @Override
    public int getCount() {
        return basePagersList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = basePagersList.get(position);
        container.addView(basePagersList.get(position).rootView);
        return basePagersList.get(position).rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

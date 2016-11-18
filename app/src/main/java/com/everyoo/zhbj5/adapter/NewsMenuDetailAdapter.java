package com.everyoo.zhbj5.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.everyoo.zhbj5.base.TabDetailPager;
import com.everyoo.zhbj5.domain.NewsData;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/18.
 */
public class NewsMenuDetailAdapter extends PagerAdapter {
    private ArrayList<TabDetailPager> mPagerList;
    private ArrayList<NewsData.NewsTabData> mChildren;

    public NewsMenuDetailAdapter(ArrayList<TabDetailPager> mPagerList, ArrayList<NewsData.NewsTabData> mChildren) {
        this.mPagerList = mPagerList;
        this.mChildren = mChildren;

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mChildren.get(position).title;
        //return newTitle.get(position);
    }

    @Override
    public int getCount() {
        return mPagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mPagerList.get(position).mRootView);
        mPagerList.get(position).initData();
        return mPagerList.get(position).mRootView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
package com.everyoo.zhbj5.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everyoo.zhbj5.MainActivity;
import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.base.BaseMenuDetailPager;
import com.everyoo.zhbj5.base.TabDetailPager;
import com.everyoo.zhbj5.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-6.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private ArrayList<TabDetailPager> mPagerList;
    private ArrayList<NewsData.NewsTabData> mChildren;
    private TabPageIndicator indicator;
    private ImageButton btnNext;

    private String[] newsTitle = {"北京", "中国", "国际", "体育",
            "生活", "旅游", "科技", "军事", "时尚",
            "财经", "袁培凯", "汽车"};

    private ArrayList<String> newTitle = new ArrayList<>();


    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        mChildren = children;
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextItem();
            }
        });

        return view;
    }

    @Override
    public void initData() {

        mPagerList = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity, mChildren.get(i));
            mPagerList.add(tabDetailPager);
        }


        for (int i = 0; i < newsTitle.length; i++) {
            newTitle.add(newsTitle[i]);
        }


        mViewPager.setAdapter(new MyAdapter());
        indicator.setViewPager(mViewPager);
        indicator.setOnPageChangeListener(this);

    }


    public void nextItem() {
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem, false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//这个方法监听也没的移动情况，单位：px

    }

    @Override
    public void onPageSelected(int position) {//这个方法监听选中的那个页面
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (position == 0) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {//这个方法监听页面滚动的状态。0:  ； 1:  ；  2：；

    }


    class MyAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            // return mChildren.get(position).title;
            return newTitle.get(position);
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


}

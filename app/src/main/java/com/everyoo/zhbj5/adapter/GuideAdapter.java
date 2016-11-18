package com.everyoo.zhbj5.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/18.
 */
public class GuideAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViewArrayList;


    public GuideAdapter(ArrayList<ImageView> imageViewArrayLis) {
        this.imageViewArrayList = imageViewArrayLis;
    }


    @Override
    public int getCount() {
        System.out.println("GuideAdapter.getCount");
        return imageViewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        System.out.println("GuideAdapter.isViewFromObject");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewArrayList.get(position));
        System.out.println("GuideAdapter.instantiateItem");
        return imageViewArrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("GuideAdapter.destroyItem");
        container.removeView((View) object);
    }
}

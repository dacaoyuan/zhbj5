package com.everyoo.zhbj5;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static final int[] imageViews = {
            R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3
    };
    private ArrayList<ImageView> imageViewArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        intView();
        viewPager.setAdapter(new GuideAdapter());


    }

    /**
     * 初始化界面
     */
    private void intView() {
        imageViewArrayList = new ArrayList<>();
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imag = new ImageView(this);
            imag.setBackgroundResource(imageViews[i]);
            imageViewArrayList.add(imag);
        }
    }


    class GuideAdapter extends PagerAdapter {


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
}

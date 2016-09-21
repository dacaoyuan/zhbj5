package com.everyoo.zhbj5;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static final int[] imageViews = {
            R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3
    };
    private ArrayList<ImageView> imageViewArrayList;

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        startBtn = (Button) findViewById(R.id.button);
        intView();
        viewPager.setAdapter(new GuideAdapter());


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    startBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });


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

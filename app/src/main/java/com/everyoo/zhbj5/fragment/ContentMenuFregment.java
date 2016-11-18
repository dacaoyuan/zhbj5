package com.everyoo.zhbj5.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.adapter.MyContentAdapter;
import com.everyoo.zhbj5.base.BasePager;
import com.everyoo.zhbj5.base.impl.GovAffairsPager;
import com.everyoo.zhbj5.base.impl.HomePager;
import com.everyoo.zhbj5.base.impl.NewsPager;
import com.everyoo.zhbj5.base.impl.SettingPager;
import com.everyoo.zhbj5.base.impl.SmartServicePager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by abc on 2016/9/22.
 */
public class ContentMenuFregment extends BaseFregment {

    @ViewInject(R.id.rg_group)
    private RadioGroup radioGroup;

    private ArrayList<BasePager> basePagersList;

    @ViewInject(R.id.id_vp)
    private ViewPager viewPager;


    @Override
    public View initViews() {
        //View view = View.inflate(mActivity, R.layout.fragment_content, null);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_content, null);
        x.view().inject(this, view);


        return view;
    }


    @Override
    public void initDate() {
        radioGroup.check(R.id.rb_home);

        basePagersList = new ArrayList<>();
        /*for (int i = 0; i < 5; i++) {
            BasePager basePage = new BasePager(mActivity);
            basePagersList.add(basePage);

        }*/

        basePagersList.add(new HomePager(mActivity));
        basePagersList.add(new NewsPager(mActivity));
        basePagersList.add(new SmartServicePager(mActivity));
        basePagersList.add(new GovAffairsPager(mActivity));
        basePagersList.add(new SettingPager(mActivity));


        viewPager.setAdapter(new MyContentAdapter(basePagersList));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        viewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                basePagersList.get(position).initData();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        basePagersList.get(0).initData();

    }


    public NewsPager newsPager() {
        return (NewsPager) basePagersList.get(1);
    }


}

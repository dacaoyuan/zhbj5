package com.everyoo.zhbj5.base.impl;

import android.app.Activity;
import android.widget.TextView;

import com.everyoo.zhbj5.base.BasePager;

/**
 * Created by Administrator on 2016-9-22.
 */
public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public void initData() {
        tvTile.setText("人口服务");
        TextView textView = new TextView(mActivity);
        textView.setText("我是服务");
        frameLayout.addView(textView);
    }
}

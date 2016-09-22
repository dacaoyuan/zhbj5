package com.everyoo.zhbj5.base.impl;

import android.app.Activity;
import android.widget.TextView;

import com.everyoo.zhbj5.base.BasePager;

/**
 * Created by Administrator on 2016-9-22.
 */
public class SettingPager extends BasePager {

    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public void initData() {
        tvTile.setText("设置");
        TextView textView = new TextView(mActivity);
        textView.setText("我是设置");
        frameLayout.addView(textView);
    }
}

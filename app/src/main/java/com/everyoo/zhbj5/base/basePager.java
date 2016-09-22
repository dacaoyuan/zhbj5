package com.everyoo.zhbj5.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.everyoo.zhbj5.R;

/**
 * Created by Administrator on 2016-9-22.
 */
public class BasePager {

    public Activity mActivity;

    public View rootView;

    public FrameLayout frameLayout;
    public TextView tvTile;


    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();
    }


    public void initViews() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.base_pager, null);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.fl_content);
        tvTile = (TextView) rootView.findViewById(R.id.tv_title);
    }


    public void initData() {
    }


}

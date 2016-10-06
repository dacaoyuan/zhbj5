package com.everyoo.zhbj5.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016-10-6.
 */

public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
    }


    public abstract View initViews();

    public void initData() {

    }

}

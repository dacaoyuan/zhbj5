package com.everyoo.zhbj5.base.menudetail;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.everyoo.zhbj5.base.BaseMenuDetailPager;

/**
 * Created by Administrator on 2016-10-6.
 */

public class InteractMenuDetailPager extends BaseMenuDetailPager {

    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {

        TextView textView = new TextView(mActivity);
        textView.setText("我是互动");

        return textView;
    }


}

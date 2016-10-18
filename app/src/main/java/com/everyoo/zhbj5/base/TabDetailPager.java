package com.everyoo.zhbj5.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.everyoo.zhbj5.domain.NewsData;

/**
 * Created by Administrator on 2016-10-6.
 */

public class TabDetailPager extends BaseMenuDetailPager {


    private final NewsData.NewsTabData childrenDate;
    private TextView textView;

    public TabDetailPager(Activity activity, NewsData.NewsTabData children) {
        super(activity);
        childrenDate = children;
    }

    @Override
    public View initViews() {

        textView = new TextView(mActivity);
        textView.setText("没有数据");

        return textView;
    }

    @Override
    public void initData() {
           textView.setText(childrenDate.title);
    }
}

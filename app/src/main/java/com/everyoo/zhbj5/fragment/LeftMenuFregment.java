package com.everyoo.zhbj5.fragment;

import android.util.Log;
import android.view.View;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.domain.NewsData;

/**
 * Created by abc on 2016/9/22.
 */
public class LeftMenuFregment extends BaseFregment {

    private final String TAG = "LeftMenuFregment";

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        return view;
    }


    @Override
    public void initDate() {


    }

    public void setNewsData(NewsData newsData) {
        NewsData newsData1 = newsData;
        Log.i(TAG, "setNewsData: newsData1=" + newsData1.retcode);

    }
}

package com.everyoo.zhbj5.base.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.everyoo.zhbj5.MainActivity;
import com.everyoo.zhbj5.base.BasePager;
import com.everyoo.zhbj5.domain.NewsData;
import com.everyoo.zhbj5.fragment.LeftMenuFregment;
import com.everyoo.zhbj5.global.GlobalContants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016-9-22.
 */
public class NewsPager extends BasePager {

    private final String TAG = "NewsPager ";

    private ProgressDialog progressdialog;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();


    }

    @Override
    public void initData() {
        tvTile.setText("新闻");
        TextView textView = new TextView(mActivity);
        textView.setText("我是新闻");
        frameLayout.addView(textView);
        setSlidingMenuEnable(true);

        getDataFromService();


    }

    private void getDataFromService() {

        progressdialog = new ProgressDialog(mActivity);
        progressdialog.setMessage("请求数据...");
        progressdialog.show();


        String url = GlobalContants.CATEGORIES_URL;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Log.i(TAG, "onSuccess: result=" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("retcode");
                    Log.i(TAG, "onSuccess: code=" + code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                parseData(result);

                progressdialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressdialog.cancel();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                progressdialog.cancel();
            }
        });


    }

    private void parseData(String result) {
        Gson gson = new Gson();
        NewsData newsData = gson.fromJson(result, NewsData.class);
        Log.i(TAG, "parseData: newsData=" + newsData);
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFregment leftMenuFregment = mainUI.leftMenuFregment();
        leftMenuFregment.setNewsData(newsData);


    }


}

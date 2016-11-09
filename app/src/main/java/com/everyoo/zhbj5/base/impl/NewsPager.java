package com.everyoo.zhbj5.base.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.everyoo.zhbj5.MainActivity;
import com.everyoo.zhbj5.base.BaseMenuDetailPager;
import com.everyoo.zhbj5.base.BasePager;
import com.everyoo.zhbj5.base.menudetail.InteractMenuDetailPager;
import com.everyoo.zhbj5.base.menudetail.NewsMenuDetailPager;
import com.everyoo.zhbj5.base.menudetail.PhotoMenuDetailPager;
import com.everyoo.zhbj5.base.menudetail.TopicMenuDetailPager;
import com.everyoo.zhbj5.domain.NewsData;
import com.everyoo.zhbj5.fragment.LeftMenuFregment;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.CacheUtils;
import com.everyoo.zhbj5.utils.MD5Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-9-22.
 */
public class NewsPager extends BasePager {

    private final String TAG = "NewsPager ";

    private ProgressDialog progressdialog;

    public ArrayList<BaseMenuDetailPager> baseMenuDetailArrayList;
    private NewsData newsData;

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
        setSlidingMenuEnable(true);

        String myCache = CacheUtils.getCache(GlobalContants.CATEGORIES_URL, mActivity);
        Log.i(TAG, "initData: myCache=" + myCache);
        if (!TextUtils.isEmpty(myCache)) {
            parseData(myCache);
        } //else {
            getDataFromService();
      //  }
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
               // String md5Url = MD5Util.get32MD5Str(GlobalContants.CATEGORIES_URL);
               // Log.i(TAG, "onSuccess: md5Url=" + md5Url);
                CacheUtils.setCache(GlobalContants.CATEGORIES_URL, result, mActivity);//设置缓存，其实也就是把整个json串，保存到SharedPreferences中

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
        newsData = gson.fromJson(result, NewsData.class);
        Log.i(TAG, "parseData: newsData=" + newsData);
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFregment leftMenuFregment = mainUI.leftMenuFregment();
        leftMenuFregment.setNewsData(newsData);

        baseMenuDetailArrayList = new ArrayList<>();
        baseMenuDetailArrayList.add(new NewsMenuDetailPager(mActivity, newsData.data.get(0).children));
        baseMenuDetailArrayList.add(new TopicMenuDetailPager(mActivity));
        baseMenuDetailArrayList.add(new PhotoMenuDetailPager(mActivity));
        baseMenuDetailArrayList.add(new InteractMenuDetailPager(mActivity));


        //frameLayout.addView(baseMenuDetailArrayList.get(0).mRootView);
        setCurrentMenuDetailPager(0);

    }


    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager baseMenuDetailPager = baseMenuDetailArrayList.get(position);
        frameLayout.removeAllViews();
        frameLayout.addView(baseMenuDetailPager.mRootView);

        tvTile.setText(newsData.data.get(position).title);

        baseMenuDetailPager.initData();

    }


}

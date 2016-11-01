package com.everyoo.zhbj5.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.domain.NewsData;
import com.everyoo.zhbj5.domain.TabData;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.Until;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016-10-6.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "TabDetailPager";

    private final NewsData.NewsTabData childrenDate;
    private TextView textView;
    private ProgressDialog progressdialog;
    private TabData tabData;

    @ViewInject(R.id.vp_news)
    private ViewPager newsViewPager;
    private ImageOptions options;

    public TabDetailPager(Activity activity, NewsData.NewsTabData children) {
        super(activity);
        childrenDate = children;
    }

    @Override
    public View initViews() {
        //View v=  LayoutInflater.from(mActivity).inflate(R.layout.tab_detail_pager,null);
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        progressdialog = new ProgressDialog(mActivity);
        progressdialog.setMessage("loading...");
        progressdialog.show();

        String url = GlobalContants.SERVER_URL + childrenDate.url;
        RequestParams params = new RequestParams(url);
        Log.i(TAG, "getDataFromServer: params=" + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ToastUtils.showToast(mActivity, "访问成功");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Log.i(TAG, "onSuccess: retcode= " + jsonObject.optInt("retcode"));
                    if (jsonObject.optInt("retcode") == 200) {
                        parseDate(result);//解析数据
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressdialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showToast(mActivity, "访问失败");
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

    private void parseDate(String result) {
        Gson gson = new Gson();
        tabData = gson.fromJson(result, TabData.class);
        // Log.i(TAG, "parseDate: = " + tabData);


        newsViewPager.setAdapter(new newsPagerAdapter());

    }

    private class newsPagerAdapter extends PagerAdapter {

        public newsPagerAdapter() {
        }

        @Override
        public int getCount() {
            return tabData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            // Log.i(TAG, "instantiateItem: " + tabData.data.topnews.get(position).topimage);
            //Log.i(TAG, "instantiateItem:url= " + processString(tabData.data.topnews.get(position).topimage));
            String imageUrl = GlobalContants.SERVER_URL + processString(tabData.data.topnews.get(position).topimage);
           // imageView.setScaleType(ImageView.ScaleType.FIT_XY);//基于控件大小去填充
            setPic();
            x.image().bind(imageView, imageUrl, options);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 截取字符串
     *
     * @return
     */
    private String processString(String url) {
        String mUrl = url.replace("http://10.0.2.2:8080/zhbj", "");
        return mUrl;
    }


    private void setPic() {
        options = new ImageOptions.Builder()
                .setFadeIn(true)
                .setFailureDrawableId(R.mipmap.topnews_item_default)
                .setLoadingDrawableId(R.mipmap.topnews_item_default)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
    }


}

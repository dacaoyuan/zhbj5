package com.everyoo.zhbj5.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.everyoo.zhbj5.NewsDetailActivity;
import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.domain.NewsData;
import com.everyoo.zhbj5.domain.TabData;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.PrefUtils;
import com.everyoo.zhbj5.utils.ToastUtils;
import com.everyoo.zhbj5.view.RefreshListView;
import com.google.gson.Gson;
import com.google.gson.annotations.Until;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-6.
 */

public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    private static final String TAG = "TabDetailPager";

    private final NewsData.NewsTabData childrenDate;
    private TextView textView;
    private ProgressDialog progressdialog;
    private TabData tabData;

    @ViewInject(R.id.vp_news)
    private ViewPager newsViewPager;
    private ImageOptions options;

    @ViewInject(R.id.title)
    private TextView topTitle;

    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;

    @ViewInject(R.id.lv_list)
    private RefreshListView listView;

    private ArrayList<TabData.NewsData> topnews;
    private ArrayList<TabData.NewsTab> newsDatas;
    private String moreUrl;
    private MyadAdapter myadAdapter;
    //private ArrayList<TabData.NewsData> newsDatas;


    public TabDetailPager(Activity activity, NewsData.NewsTabData children) {
        super(activity);
        childrenDate = children;
    }

    @Override
    public View initViews() {
        //View v=  LayoutInflater.from(mActivity).inflate(R.layout.tab_detail_pager,null);
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        x.view().inject(this, view);

        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);
        x.view().inject(this, headerView);

        listView.addHeaderView(headerView);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: moreUrl=" + moreUrl);
                if (moreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    ToastUtils.showToast(mActivity, "last pager!");
                    Log.i(TAG, "onLoadMore: last pager!");
                    listView.onRefreshComplete(false);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                String readIds = ids + newsDatas.get(position).id;
                if (!ids.contains(readIds)) {
                    ids = ids + readIds + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                // myadAdapter.notifyDataSetChanged();
                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象

                String mUrl = GlobalContants.SERVER_URL + processString(newsDatas.get(position).url);

                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("list_url", mUrl);
                mActivity.startActivity(intent);

            }
        });

        return view;
    }


    /**
     * 改变已读新闻的颜色
     */
    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
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
                        parseDate(result, false);//解析数据
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.onRefreshComplete(true);
                progressdialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showToast(mActivity, "访问失败");
                listView.onRefreshComplete(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                listView.onRefreshComplete(true);
                progressdialog.cancel();
            }
        });

    }

    private void getMoreDataFromServer() {
        progressdialog = new ProgressDialog(mActivity);
        progressdialog.setMessage("loading...");
        progressdialog.show();

        String url = moreUrl;
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
                        parseDate(result, true);//解析数据
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.onRefreshComplete(true);
                progressdialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showToast(mActivity, "访问失败");
                listView.onRefreshComplete(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                listView.onRefreshComplete(true);
                progressdialog.cancel();
            }
        });

    }


    private void parseDate(String result, boolean isMore) {
        Gson gson = new Gson();
        tabData = gson.fromJson(result, TabData.class);
        // Log.i(TAG, "parseDate: = " + tabData);

        moreUrl = tabData.data.more;
        if (!TextUtils.isEmpty(moreUrl)) {
            moreUrl = GlobalContants.SERVER_URL + tabData.data.more;
        } else {
            moreUrl = null;
        }

        if (!isMore) {
            newsViewPager.setAdapter(new newsPagerAdapter());
            topnews = tabData.data.topnews;
            if (topnews != null) {
                topTitle.setText(topnews.get(0).title);
                newsViewPager.addOnPageChangeListener(this);
                indicator.setViewPager(newsViewPager);
                // indicator.onPageSelected(0);//让指示器重新定位到第一个点  我发现不调用也能实现功能。
            } else {
                Log.i(TAG, "topnews is null ");
            }

            newsDatas = tabData.data.news;
            if (newsDatas != null) {
                myadAdapter = new MyadAdapter();
                listView.setAdapter(myadAdapter);
            } else {
                Log.i(TAG, "newsDatas is null ");
            }
        } else {//如果是加载下一页数据，将数据追加给原来的list集合，也就是newsDatas
            ArrayList<TabData.NewsTab> news = tabData.data.news;
            newsDatas.addAll(news);
            myadAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        topTitle.setText(topnews.get(position).title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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


    public class MyadAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //   View view;
            ViewHolder holer;

            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_news_item, null);
                holer = new ViewHolder();

                holer.image = (ImageView) convertView.findViewById(R.id.iv_pic);
                holer.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holer.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(holer);

                 /* ImageView imageView = (ImageView) view.findViewById(R.id.iv_pic);
                TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
                TextView textData = (TextView) view.findViewById(R.id.tv_date);
                textTitle.setText(newsDatas.get(position).title);
                textData.setText(newsDatas.get(position).pubdate);*/
            } else {
                // view = convertView;
                holer = (ViewHolder) convertView.getTag();
            }
            holer.tvTitle.setText(newsDatas.get(position).title);
            holer.tvDate.setText(newsDatas.get(position).pubdate);
            String imgUrl = GlobalContants.SERVER_URL + processString(newsDatas.get(position).listimage);
            //Log.i(TAG, "getView: listimage=" + imgUrl);
            setPic();
            x.image().bind(holer.image, imgUrl, options);

            String readId = PrefUtils.getString(mActivity, "read_ids", "");
            if (readId.contains(newsDatas.get(position).id + "")) {
                holer.tvTitle.setTextColor(Color.BLUE);
                holer.tvDate.setTextColor(Color.BLUE);
            } else {
                holer.tvTitle.setTextColor(Color.BLACK);
                holer.tvDate.setTextColor(Color.BLACK);
            }


            return convertView;
        }
    }

    public static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView image;
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

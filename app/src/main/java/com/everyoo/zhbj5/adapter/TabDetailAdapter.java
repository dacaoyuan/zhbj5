package com.everyoo.zhbj5.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.base.TabDetailPager;
import com.everyoo.zhbj5.domain.TabData;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.PrefUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/18.
 */


public class TabDetailAdapter extends BaseAdapter {
    private ArrayList<TabData.NewsTab> newsDatas;
    private Activity mActivity;
    private ImageOptions options;
    private TabDetailPager.NewsPagerAdapter pagerAdapter;

    public TabDetailAdapter(ArrayList<TabData.NewsTab> newsDatas, Activity mActivity, TabDetailPager.NewsPagerAdapter pagerAdapter) {
        this.newsDatas = newsDatas;
        this.mActivity = mActivity;
        this.pagerAdapter = pagerAdapter;
    }


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
            pagerAdapter.notifyDataSetChanged();//这样每次不为null时，都手动通知ViewPager去更新adapter.
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

        /**
         * 目前大致理解如下：可能由于23以上的api对adapter的刷新要求更高一些，由于listView 在重新绘制的时候
         * ,新闻，的那个viewPager的适配器还没有来得及，刷新。导致报错了。
         */
       // pagerAdapter.notifyDataSetChanged();//这样每次重新绘制时，都手动通知ViewPager去更新adapter.
        return convertView;
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


    /**
     * 截取字符串
     *
     * @return
     */
    private String processString(String url) {
        String mUrl = url.replace("http://10.0.2.2:8080/zhbj", "");
        return mUrl;
    }

}


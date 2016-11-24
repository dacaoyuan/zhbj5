package com.everyoo.zhbj5.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.domain.PhotosData;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.Xutils;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by abc on 2016/11/21.
 */
public class PhotoAdapter extends BaseAdapter {
    private ArrayList<PhotosData.photoNews> photoNewsArrayList;
    private Activity mActivity;

    public PhotoAdapter(ArrayList<PhotosData.photoNews> photoNewsArrayList, Activity mActivity) {
        this.photoNewsArrayList = photoNewsArrayList;
        this.mActivity = mActivity;
    }


    @Override
    public int getCount() {
        return photoNewsArrayList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.list_photo_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvTitle.setText(photoNewsArrayList.get(position).title);
        String imaUrl = GlobalContants.SERVER_URL + Xutils.processString(photoNewsArrayList.get(position).listimage);
        x.image().bind(holder.imageView, imaUrl);


        return convertView;
    }

    static class ViewHolder {
        public TextView tvTitle;
        public ImageView imageView;

    }
}




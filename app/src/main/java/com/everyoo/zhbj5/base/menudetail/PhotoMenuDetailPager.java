package com.everyoo.zhbj5.base.menudetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.base.BaseMenuDetailPager;
import com.everyoo.zhbj5.domain.PhotosData;
import com.everyoo.zhbj5.global.GlobalContants;
import com.everyoo.zhbj5.utils.CacheUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-6.
 */

public class PhotoMenuDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "PhotoMenuDetailPager";
    // @ViewInject(R.id.lv_photo)
    private ListView listView;

    //  @ViewInject(R.id.gv_photo)
    private GridView gridView;

    private PhotoAdapter photoAdapter;
    private ProgressDialog progressdialog;
    private PhotosData photosData;
    private ArrayList<PhotosData.photoNews> photoNewsArrayList;
    private ImageButton btnPhptos;


    public PhotoMenuDetailPager(Activity activity, ImageButton imagePhotosButton) {
        super(activity);
        btnPhptos = imagePhotosButton;
        btnPhptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    private boolean isListDisplay = true;

    /**
     * 视图切换展示
     */
    private void changeDisplay() {


        if (isListDisplay) {
            isListDisplay = false;
            listView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            btnPhptos.setImageResource(R.mipmap.icon_pic_list_type);
        } else {
            isListDisplay = true;
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            btnPhptos.setImageResource(R.mipmap.icon_pic_grid_type);
        }


    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        //  x.view().inject(view);
        listView = (ListView) view.findViewById(R.id.lv_photo);
        gridView = (GridView) view.findViewById(R.id.gv_photo);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        String mCache = CacheUtils.getCache(GlobalContants.PHOTOS_URL, mActivity);
        Log.i(TAG, "initData: mCache=" + mCache);
        if (!TextUtils.isEmpty(mCache)) {
            parseData(mCache);
        }

        getDataFromServer();
    }

    private void getDataFromServer() {

        String url = GlobalContants.PHOTOS_URL;
        RequestParams params = new RequestParams(url);
        Log.i(TAG, "getDataFromServer: params=" + params);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // Log.i(TAG, "onSuccess: result=" + result);

                progressdialog = new ProgressDialog(mActivity);
                progressdialog.setMessage("请求数据...");
                progressdialog.show();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int mRetcode = jsonObject.optInt("retcode");
                    Log.i(TAG, "onSuccess: mRetcode= " + mRetcode);
                    if (mRetcode == 200) {
                        parseData(result);
                        CacheUtils.setCache(GlobalContants.PHOTOS_URL, result, mActivity);//设置缓存，其实也就是把整个json串，保存到SharedPreferences中
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressdialog.cancel();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                progressdialog.cancel();
            }

            @Override
            public void onFinished() {
                progressdialog.cancel();
            }
        });


    }

    private void parseData(String result) {
        Gson gson = new Gson();
        photosData = gson.fromJson(result, PhotosData.class);
        Log.i(TAG, "parseData photosData= " + photosData);

        photoNewsArrayList = photosData.data.news;
        if (photoNewsArrayList != null) {
            photoAdapter = new PhotoAdapter();
            listView.setAdapter(photoAdapter);
            gridView.setAdapter(photoAdapter);
        } else {
            Log.i(TAG, "photoNewsArrayList is null");
        }


    }


    class PhotoAdapter extends BaseAdapter {

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
            String imaUrl = GlobalContants.SERVER_URL + processString(photoNewsArrayList.get(position).listimage);
            x.image().bind(holder.imageView, imaUrl);


            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public ImageView imageView;

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

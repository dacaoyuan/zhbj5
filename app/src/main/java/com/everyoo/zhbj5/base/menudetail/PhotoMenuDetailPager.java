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
import com.everyoo.zhbj5.adapter.PhotoAdapter;
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

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by Administrator on 2016-10-6.
 */

public class PhotoMenuDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "PhotoMenuDetailPager";
    // @ViewInject(R.id.lv_photo)
    private ListView listView;

    //  @ViewInject(R.id.gv_photo)
    //private GridView gridView;
    private PtrFrameLayout ptr;


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
          //  gridView.setVisibility(View.GONE);
            btnPhptos.setImageResource(R.mipmap.icon_pic_list_type);
        } else {
            isListDisplay = true;
            listView.setVisibility(View.GONE);
           // gridView.setVisibility(View.VISIBLE);
            btnPhptos.setImageResource(R.mipmap.icon_pic_grid_type);
        }


    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        //  x.view().inject(view);
        listView = (ListView) view.findViewById(R.id.lv_photo);
       // gridView = (GridView) view.findViewById(R.id.gv_photo);

        ptr = (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
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
        pullRefresh();
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
            photoAdapter = new PhotoAdapter(photoNewsArrayList, mActivity);
            listView.setAdapter(photoAdapter);
            //gridView.setAdapter(photoAdapter);
        } else {
            Log.i(TAG, "photoNewsArrayList is null");
        }


    }


    public void pullRefresh() {
        final MaterialHeader header = new MaterialHeader(mActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);
        ptr.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromServer();
                ptr.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("GovAffairsPager.run postDelayed");
                        photoAdapter.notifyDataSetChanged();
                        ptr.refreshComplete();
                        //listView.smoothScrollToPosition(0);//能让listview 平滑滚动到 0 位置

                    }
                }, 1500);//这个2秒的意思就是，2秒后，头布局隐藏掉。
            }
        });
    }


}

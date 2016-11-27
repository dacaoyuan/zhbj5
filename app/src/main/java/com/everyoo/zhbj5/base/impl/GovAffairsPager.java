package com.everyoo.zhbj5.base.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.base.BasePager;
import com.everyoo.zhbj5.domain.GovAffairsData;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by Administrator on 2016-9-22.
 */
public class GovAffairsPager extends BasePager {

    private final static String TAG = "GovAffairsPager ";

    private ArrayList<String> arrayList;

    private ListView listView;
    private View view;

    private PtrFrameLayout ptr;


    public GovAffairsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public void initData() {
        tvTile.setText("政务");
        setSlidingMenuEnable(true);

        view = LayoutInflater.from(mActivity).inflate(R.layout.gov_list_item, null);


        listView = (ListView) view.findViewById(R.id.gov_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mActivity, arrayList.get(position), Toast.LENGTH_LONG).show();
            }
        });

        ptr = (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
        final MaterialHeader header = new MaterialHeader(mActivity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                System.out.println("GovAffairsPager.checkCanDoRefresh");
                if (listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0) {
                    return true;
                }
                return false;
                // return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("GovAffairsPager.onRefreshBegin");
                getFromService();
                ptr.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("GovAffairsPager.run postDelayed");
                        ptr.refreshComplete();

                    }
                }, 1500);//这个2秒的意思就是，2秒后，头布局隐藏掉。

            }
        });


        getFromService();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            frameLayout.removeAllViews();
            frameLayout.addView(view);
            System.out.println("GovAffairsPager.handleMessage");
          /*  ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(mActivity, R.layout.gov_list_item, arrayList);
            listView.setAdapter(stringArrayAdapter);*/
            listView.setAdapter(new GovAdapter());

        }
    };


    class GovAdapter extends BaseAdapter {

        private TextView textName;

        private GovAdapter() {

        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view1;
            if (convertView == null) {
                view1 = View.inflate(mActivity, R.layout.gov_list_item2, null);
            } else {
                view1 = convertView;
            }

            textName = (TextView) view1.findViewById(R.id.text2);
            textName.setText(arrayList.get(position));

            return view1;
        }
    }


    private void getFromService() {

        final ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("正在请求数据...");
        progressDialog.show();


        String url = "http://221.214.212.46:8009/fszwservice.asmx/getAllHxInfo?";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("userID", "1");
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                Log.i(TAG, "onSuccess: result=" + result);
                parsData(result);


                progressDialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                progressDialog.cancel();
            }
        });


    }


    private void parsData(String result) {

        Gson gson = new Gson();
        GovAffairsData govAffairsData = gson.fromJson(result, GovAffairsData.class);
        //Log.i(TAG, "parsData: govAffairsData.getMsgCode()=" + govAffairsData.getMsgCode());


        Log.i(TAG, "parsData: =" + govAffairsData);

        if (govAffairsData.getMsgCode() == 1) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < govAffairsData.getEntity().size(); i++) {
                arrayList.add(govAffairsData.getEntity().get(i).getNickName());
            }

            handler.sendEmptyMessage(0);

            /*for (GovAffairsData.ChlidrenData chlidrenData : govAffairsData.getChlidrenDatas()) {

            }*/


        }


    }
}

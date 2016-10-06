package com.everyoo.zhbj5.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.everyoo.zhbj5.MainActivity;
import com.everyoo.zhbj5.R;
import com.everyoo.zhbj5.base.BasePager;
import com.everyoo.zhbj5.base.impl.NewsPager;
import com.everyoo.zhbj5.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by abc on 2016/9/22.
 */
public class LeftMenuFregment extends BaseFregment {

    private final String TAG = "LeftMenuFregment";


    @ViewInject(R.id.lv_list)
    private ListView listView;
    private ArrayList<NewsData.NewsMenuData> stringArrayList;
    private int setCrentPos;
    private MenuAdapter mAdapter;
    private MainActivity mainUi;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        x.view().inject(this, view);
        return view;
    }


    @Override
    public void initDate() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCrentPos = position;
                mAdapter.notifyDataSetChanged();

                mainUi = (MainActivity) mActivity;
                ContentMenuFregment contentMenuFregment = mainUi.contentMenuFregment();
                NewsPager newsPager = contentMenuFregment.newsPager();
                newsPager.setCurrentMenuDetailPager(position);
                toggleSlidingMenu();

            }
        });

    }

    private void toggleSlidingMenu() {
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }


    class MenuAdapter extends BaseAdapter {

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return stringArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            // if (convertView == null) {
            view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvTitle.setText(stringArrayList.get(position).title);
            //  } else {
            //    view = convertView;
            //  }

            if (setCrentPos == position) {
                tvTitle.setEnabled(true);
            } else {
                tvTitle.setEnabled(false);
            }


            return view;
        }
    }


    public void setNewsData(NewsData newsData) {
        NewsData newsData1 = newsData;
        Log.i(TAG, "setNewsData: newsData1=" + newsData1.retcode);

        /*for (int i = 0; i < newsData1.data.size(); i++) {
            Log.i(TAG, "setNewsData: title=" + newsData1.data.get(i).title);
            stringArrayList.add(newsData1.data.get(i).title);

        }*/

        stringArrayList = newsData1.data;
        mAdapter = new MenuAdapter();
        listView.setAdapter(mAdapter);
    }
}

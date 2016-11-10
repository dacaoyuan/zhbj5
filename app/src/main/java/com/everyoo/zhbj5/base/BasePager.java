package com.everyoo.zhbj5.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.everyoo.zhbj5.MainActivity;
import com.everyoo.zhbj5.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Administrator on 2016-9-22.
 */
public class BasePager {

    public Activity mActivity;
    public View rootView;

    public FrameLayout frameLayout;
    public TextView tvTile;
    public ImageButton imageButton;
    public ImageButton imagePhotosButton;


    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();

    }


    public void initViews() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.base_pager, null);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.fl_content);
        tvTile = (TextView) rootView.findViewById(R.id.tv_title);
        imageButton = (ImageButton) rootView.findViewById(R.id.btn_menu);
        imagePhotosButton = (ImageButton) rootView.findViewById(R.id.btn_photos);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }


    public void initData() {
    }


    /**
     * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa(反之亦然).
     */
    private void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }


    public void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }


}

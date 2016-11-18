package com.everyoo.zhbj5.view;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by abc on 2016/11/10.
 * 头条新闻的触摸监听
 */
public class TopNewsTouchListener implements View.OnTouchListener {

    private Handler mHandler;

    public TopNewsTouchListener(Handler handler) {
        mHandler = handler;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下");
               mHandler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息

                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("事件取消");
                mHandler.sendEmptyMessageDelayed(0, 3000);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("抬起");
               mHandler.sendEmptyMessageDelayed(0, 3000);
                break;

            default:
                break;
        }

        return true;
    }
}

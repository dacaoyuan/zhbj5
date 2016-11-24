package com.everyoo.zhbj5;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by abc on 2016/9/21.
 */
public class InitActivity extends Application {

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.
    }
}

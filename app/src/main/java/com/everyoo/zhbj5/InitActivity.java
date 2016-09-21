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
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}

package com.everyoo.zhbj5.utils;

import android.content.Context;

/**
 * Created by abc on 2016/11/9.
 */
public class CacheUtils {

    /**
     * 设置缓存   核心思想：就是把整个json串，保存到SharedPreferences中
     */
    public static void setCache(String key, String value, Context ctx) {
        PrefUtils.setString(ctx, key, value);
    }


    /**
     * 获取缓存
     */
    public static String getCache(String key, Context ctx) {
        return PrefUtils.getString(ctx, key, null);
    }

}

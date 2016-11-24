package com.everyoo.zhbj5.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by abc on 2016/11/1.
 */
public class ToastUtils {

    public static Toast toast;

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }




}

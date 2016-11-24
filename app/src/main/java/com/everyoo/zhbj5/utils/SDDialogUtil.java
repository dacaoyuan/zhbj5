package com.everyoo.zhbj5.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;

import com.everyoo.zhbj5.InitActivity;

public class SDDialogUtil {

    public static Dialog alert(CharSequence title, CharSequence message) {

        Builder builder = new Builder(InitActivity.context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    public static Dialog alert(int title, int message) {

        Builder builder = new Builder(InitActivity.context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    public static Dialog confirm(CharSequence title, CharSequence message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(InitActivity.context);
        builder.setTitle(title);
        builder.setMessage(message);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    public static Dialog confirm(int title, int message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(InitActivity.context);
        builder.setTitle(title);
        builder.setMessage(message);
        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    // 弹出自定义的窗体
    public static Dialog showView(CharSequence title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(InitActivity.context);
        if (title != "")
            builder.setTitle(title);
        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    public static Dialog showView(int title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(InitActivity.context);
        if (title != 0)
            builder.setTitle(title);
        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    // 弹出自定义的信息
    public static Dialog showMsg(CharSequence title, CharSequence message) {
        Builder builder = new Builder(InitActivity.context);
        if (title != "")
            builder.setTitle(title);
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }

    public static Dialog showMsg(int title, int message) {
        Builder builder = new Builder(InitActivity.context);
        if (title != 0)
            builder.setTitle(title);
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }


}

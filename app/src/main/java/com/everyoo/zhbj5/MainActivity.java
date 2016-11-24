package com.everyoo.zhbj5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;

import com.everyoo.zhbj5.fragment.ContentMenuFregment;
import com.everyoo.zhbj5.fragment.LeftMenuFregment;
import com.everyoo.zhbj5.utils.DialogUtil;
import com.everyoo.zhbj5.utils.SDDialogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    private static final String LEFTTAG = "leftMenuTag";
    private static final String RIGHTTAG = "rightMenuTag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(260);
        initViewFragment();

    }

    private void initViewFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.left_men, new LeftMenuFregment(), LEFTTAG);
        transaction.replace(R.id.content_men, new ContentMenuFregment(), RIGHTTAG);
        transaction.commit();
    }

    public LeftMenuFregment leftMenuFregment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        LeftMenuFregment leftMenuFregment = (LeftMenuFregment) fragmentManager.findFragmentByTag(LEFTTAG);


        return leftMenuFregment;
    }


    public ContentMenuFregment contentMenuFregment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        ContentMenuFregment contentMenuFregment = (ContentMenuFregment) fragmentManager.findFragmentByTag(RIGHTTAG);


        return contentMenuFregment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("MainActivity.onKeyDown");
        new DialogUtil(this).alert("退出", "要退出应用吗？");
        return true;
    }


    public void test() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ni");
        builder.setMessage("fasdfdas");
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }


}

package com.wang.catchcrazycat.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wang.android_lib.util.M;
import com.wang.android_lib.util.NotificationUtil;
import com.wang.android_lib.util.ScreenUtil;
import com.wang.catchcrazycat.R;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class Util {

    public static Context context;

    public static void toast(String message) {
        M.t(context, message);
    }

    public static SlidingMenu buildSlidingMenu(Activity activity, int layoutRes) {
        SlidingMenu menu = new SlidingMenu(activity);
//        设置侧滑菜单的位置，可选值LEFT , RIGHT , LEFT_RIGHT （两边都有菜单时设置）
        menu.setMode(SlidingMenu.RIGHT);
//         设置触摸屏幕的模式，可选只MARGIN , FULLSCREEN，NONE 
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        设置阴影的宽度
//        menu.setShadowWidth(10);
//        设置滑动菜单的阴影效果图
//        menu.setShadowDrawable(R.drawable.ic_loading);
//        设置菜单的宽度或离屏幕的偏移量，这两个都是设置滑动菜单视图的宽度，二选一
        menu.setBehindWidth((int) (ScreenUtil.getScreenWidth(activity) * 0.6));
//        menu.setBehindOffset(200);
//        设置是否渐入渐出效果以及其值
        menu.setFadeEnabled(true);
        menu.setFadeDegree(0.35f);
//        设置SlidingMenu与下方视图的移动的速度比，当为1时同时移动，取值0-1
        menu.setBehindScrollScale(0.2f);
//        设置二级菜单的阴影效果
//        menu.setSecondaryShadowDrawable(R.color.red);
//        为侧滑菜单设置布局
        menu.setMenu(layoutRes);
//        设置右边（二级）侧滑菜单
//        menu.setSecondaryMenu(R.layout.right_menu_frame);

//        把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);

//        切换，关闭，打开
//        menu.toggle(true);
//        menu.showContent(true);
//        menu.showMenu(true);

//        设置监听
//        menu.setOnOpenListener(null);
//        menu.setOnOpenedListener(null);
//        menu.setOnCloseListener(null);
//        menu.setOnClosedListener(null);

        return menu;
    }

    public static void showWinDialog(Context context, DialogInterface.OnClickListener playNextListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("恭喜恭喜");
        builder.setMessage("恭喜你！你过关了！");
        builder.setIcon(R.mipmap.app_icon);
        builder.setPositiveButton("挑战下一关", playNextListener);
        builder.setNegativeButton("返回", null);
        builder.create().show();
    }

    public static void showLossDialog(Context context, DialogInterface.OnClickListener replayListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sorry...");
        builder.setMessage("对不起，你输了。");
        builder.setIcon(R.mipmap.app_icon);
        builder.setPositiveButton("重来", replayListener);
        builder.setNegativeButton("返回", null);
        builder.create().show();
    }

    public static void showNotification(Context context, String message) {
        NotificationUtil.showNotification(
                context,
                0,
                R.mipmap.app_icon,
                context.getResources().getString(R.string.app_name),
                message,
                false
        );
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

}

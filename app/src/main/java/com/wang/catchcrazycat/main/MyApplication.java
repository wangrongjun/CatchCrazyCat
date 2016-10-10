package com.wang.catchcrazycat.main;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.wang.android_lib.util.NotificationUtil;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;

import java.util.Map;

import cn.bmob.v3.Bmob;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(getApplicationContext(), "d24200f6c32d090550ee469decd38cda");
        P.context = getApplicationContext();
        Util.context = getApplicationContext();

        initCrashReport(getApplicationContext(), "900055319", true);
    }

    public static void initCrashReport(final Context context, String BUGLY_APP_ID, boolean isDebug) {

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public synchronized Map<String, String> onCrashHandleStart(int i, String s, String s1, String s2) {
                String message = "异常类型：\n" + s + "\n\n\n" +
                        "异常信息：\n" + s1 + "\n\n\n" + "堆栈信息：\n" + s2;
                NotificationUtil.showNotification(
                        context, 999, R.mipmap.app_icon,
                        "围住神经猫 - 异常信息", message, false);
                return super.onCrashHandleStart(i, s, s1, s2);
            }
        });

        CrashReport.initCrashReport(context, BUGLY_APP_ID, isDebug, strategy);
    }

}

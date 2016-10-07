package com.wang.catchcrazycat.main;

import android.app.Application;

import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;

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
    }
}

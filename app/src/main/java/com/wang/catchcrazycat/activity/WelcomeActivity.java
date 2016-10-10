package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class WelcomeActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initHandler();
        delayStart();

        P.setCanUpgrade(false);
        P.setLatestVersion(null);
        Util.startCheckUpdateNotShowToPlayer(getApplicationContext());
    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                finish();
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                return true;
            }
        });
    }

    private void delayStart() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //禁止欢迎页面显示期间按返回键后退出
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}

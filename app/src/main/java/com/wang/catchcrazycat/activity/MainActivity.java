package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.WindowUtil;
import com.wang.catchcrazycat.view.Playground;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.play_ground)
    Playground playground;

    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.transStateBar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSlidingMenu();
    }

    private void initSlidingMenu() {
        slidingMenu = Util.buildSlidingMenu(this, R.layout.sliding_menu);
        slidingMenu.setBackgroundResource(R.mipmap.bg_welcome);
        slidingMenu.findViewById(R.id.btn_menu_play_new).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_choose_level).setOnClickListener(this);
    }

    private void chooseLevel() {
        DialogUtil.showInputDialog(this, "选择等级", "初始路障数量", "10", new DialogUtil.OnInputFinishListener() {
            @Override
            public void onInputFinish(String text) {
                try {
                    playground.blockNumber = Integer.parseInt(text);
                    playground.playNew();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "请输入数字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.btn_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_menu:
                slidingMenu.toggle();
                return;
            case R.id.btn_menu_play_new:
                playground.playNew();
                break;
            case R.id.btn_menu_choose_level:
                chooseLevel();
                break;
        }
        slidingMenu.toggle();
    }
}

package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wang.android_lib.util.DoubleClickToExit;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;
import com.wang.catchcrazycat.view.Playground;
import com.wang.common_lib.MaterialDialogUtil;
import com.wang.java_util.StreamUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.play_ground)
    Playground playground;
    @Bind(R.id.tv_max_level)
    TextView tvMaxLevel;
    @Bind(R.id.tv_current_level)
    TextView tvCurrentLevel;
    @Bind(R.id.tv_step)
    TextView tvStep;
    @Bind(R.id.tv_current_level_description)
    TextView tvCurrentLevelDescription;

    private SlidingMenu slidingMenu;
    private BroadcastReceiver receiver;
    private TextView tvPlayerName;

    public static final String ACTION_STEP_CHANGED = "com.wang.action.step_changed";
    public static final String ACTION_MAX_LEVEL_CHANGED = "com.wang.action.max_level_changed";
    public static final String ACTION_CURRENT_LEVEL_CHANGED = "com.wang.action.current_level_changed";
    public static final String ACTION_SHOW_PLAYER_NAME = "com.wang.action.show_player_name";
    public static final String ACTION_CHANGE_CURRENT_LEVEL = "com.wang.action.change_current_level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBroadCast();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSlidingMenu();
    }

    private void initBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_STEP_CHANGED);
        filter.addAction(ACTION_MAX_LEVEL_CHANGED);
        filter.addAction(ACTION_CURRENT_LEVEL_CHANGED);
        filter.addAction(ACTION_SHOW_PLAYER_NAME);
        filter.addAction(ACTION_CHANGE_CURRENT_LEVEL);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION_STEP_CHANGED:
                        int step = intent.getIntExtra("step", 0);
                        tvStep.setText(String.valueOf(step));
                        break;
                    case ACTION_CURRENT_LEVEL_CHANGED:
                        int currentLevel = intent.getIntExtra("currentLevel", 0);
                        tvCurrentLevel.setText(LevelRule.getLevelString(currentLevel) +
                                "(" + currentLevel + ")");
                        tvCurrentLevelDescription.setText(LevelRule.getLevelDescription(currentLevel));
                        break;
                    case ACTION_MAX_LEVEL_CHANGED:
                        int maxLevel = P.getPlayerMaxLevel();
                        tvMaxLevel.setText(LevelRule.getLevelString(maxLevel) + "(" + maxLevel + ")");
                        break;
                    case ACTION_SHOW_PLAYER_NAME:
                        tvPlayerName.setText(P.getPlayerName());
                        break;
                    case ACTION_CHANGE_CURRENT_LEVEL:
                        int newLevel = intent.getIntExtra("newLevel", LevelRule.getMinLevel());
                        playground.setCurrentLevelAndPlay(newLevel);
                        break;
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initSlidingMenu() {
        slidingMenu = Util.buildSlidingMenu(this, R.layout.sliding_menu);
        slidingMenu.setBackgroundResource(R.mipmap.bg_welcome);
        slidingMenu.findViewById(R.id.btn_menu_level_list).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_choose_level).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_challenge_again).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_game_description).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_about).setOnClickListener(this);
        slidingMenu.findViewById(R.id.btn_menu_test).setOnClickListener(this);
        tvPlayerName = (TextView) slidingMenu.findViewById(R.id.tv_player_name);
        tvPlayerName.setText(P.getPlayerName());
    }

    @OnClick({R.id.btn_menu, R.id.btn_play_new, R.id.btn_previous_level, R.id.btn_next_level})
    public void onClick(View view) {

        switch (view.getId()) {//所有侧滑菜单的按钮

            case R.id.btn_menu_level_list://侧滑菜单的封神榜按钮
                startActivity(new Intent(this, PlayerLevelListActivity.class));
                break;
            case R.id.btn_menu_choose_level://侧滑菜单的选择等级按钮
                startActivity(new Intent(this, ChooseLevelActivity.class));
                slidingMenu.toggle();
                break;
            case R.id.btn_menu_challenge_again://侧滑菜单的重新挑战按钮
                challengeAgain();
                slidingMenu.toggle();
                break;
            case R.id.btn_menu_game_description:
                showGameDescriptionDialog();
                slidingMenu.toggle();
                break;
            case R.id.btn_menu_about://侧滑菜单的关于按钮
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.btn_menu_test:
                if (LevelRule.simple) {
                    LevelRule.simple = false;
                } else {
                    LevelRule.simple = true;
                }
                slidingMenu.toggle();
                break;
        }

        switch (view.getId()) {//所有主页面的按钮

            case R.id.btn_menu:
                slidingMenu.toggle();
                break;
            case R.id.btn_previous_level:
                playground.playPreviousLevel();
                break;
            case R.id.btn_next_level:
                playground.playNextLevel(false);
                break;
            case R.id.btn_play_new:
                playground.playNew();
                break;
        }

    }

    private void showGameDescriptionDialog() {
        try {
            String gameDescription = StreamUtil.readInputStream(getAssets().open("game_description.txt"));
            MaterialDialogUtil.showConfirm(this, "游戏说明", gameDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void challengeAgain() {
        MaterialDialogUtil.showConfirm(this, "重新挑战", "重新挑战将会清空过关记录，是否重新挑战？",
                new MaterialDialogUtil.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick() {
                        P.clear();
                        playground.setCurrentLevelAndPlay(LevelRule.getMinLevel());
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return DoubleClickToExit.onKeyDown(keyCode, event, this) || super.onKeyDown(keyCode, event);
    }
}

package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.adapter.ChooseLevelAdapter;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.util.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class ChooseLevelActivity extends Activity {

    @Bind(R.id.lv_level)
    ListView lvLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        ButterKnife.bind(this);

        initListView();
    }

    private void initListView() {

        List<ChooseLevelAdapter.ListItem> items = new ArrayList<>();
        for (int level = LevelRule.getMinLevel(); level <= LevelRule.getMaxLevel(); level++) {
            ChooseLevelAdapter.ListItem item = new ChooseLevelAdapter.ListItem(
                    level,
                    LevelRule.getLevelString(level),
                    LevelRule.getLevelDescription(level),
                    level <= P.getPlayerMaxLevel() + 1 //level小于等于玩家达到的最高等级+1，可点击该level
            );
            items.add(item);
        }
        ChooseLevelAdapter adapter = new ChooseLevelAdapter(this, items);
        lvLevel.setAdapter(adapter);

        lvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedLevel = (int) id;
                if (selectedLevel <= P.getPlayerMaxLevel() + 1) {//level小于等于玩家达到的最高等级+1，可玩该level
                    finish();
                    sendDelayBroadcast(selectedLevel);
                }
            }
        });

    }

    /**
     * 延迟200毫秒，以防MainActivity还没显示，画布Canvas尚未解锁，就收到广播并重绘游戏画布，从而闪退。
     */
    private void sendDelayBroadcast(final int newLevel) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.ACTION_CHANGE_CURRENT_LEVEL);
                intent.putExtra("newLevel", newLevel);
                sendBroadcast(intent);
            }
        }, 200);

    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}

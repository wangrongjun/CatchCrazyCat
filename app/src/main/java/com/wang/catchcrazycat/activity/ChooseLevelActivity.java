package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.adapter.ChooseLevelAdapter;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.util.P;

import java.util.ArrayList;
import java.util.List;

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
                    level <= P.getMaxLevel() + 1 //level小于等于玩家达到的最高等级+1，可点击该level
            );
        }
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}

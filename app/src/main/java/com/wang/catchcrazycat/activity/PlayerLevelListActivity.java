package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.wang.android_lib.util.M;
import com.wang.android_lib.util.WindowUtil;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.adapter.PlayerLevelListAdapter;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.game.Player;
import com.wang.catchcrazycat.util.BmobUtil;
import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class PlayerLevelListActivity extends Activity {

    @Bind(R.id.lv_player_level_list)
    ListView lvPlayerLevelList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.transStateBar(this);
        setContentView(R.layout.activity_player_level_list);
        ButterKnife.bind(this);
        initSwipeRefresh();
        startGetPlayerLevelList();
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.blue_sky, R.color.orange_dark);
        swipeRefresh.measure(0, 0);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGetPlayerLevelList();
            }
        });
    }

    private void startGetPlayerLevelList() {
        swipeRefresh.setRefreshing(true);
        //TODO 尝试使用相关子查询（不设置别名）
        BmobQuery<Player> query = new BmobQuery<>();
        query.setLimit(500);
        query.order("-level,-createdAt");
        query.addWhereGreaterThan("level", LevelRule.getShowPlayerListMinLevel() - 1);
        query.findObjects(new FindListener<Player>() {
            @Override
            public void done(List<Player> players, BmobException e) {
                swipeRefresh.setRefreshing(false);
                if (e == null) {
                    handlePlayers(players);
                    showPlayerLevelList(players);
                } else {
                    String hint = BmobUtil.getExceptionHint("榜单获取失败", e);
                    M.t(PlayerLevelListActivity.this, hint);
                    Util.showNotification(PlayerLevelListActivity.this, hint);
                }
            }
        });

    }

    private void handlePlayers(List<Player> players) {

        int continueCount = 0;
        int currentLevel = LevelRule.getMaxLevel();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLevel() == currentLevel) {
                continueCount++;
            } else {
                currentLevel = players.get(i).getLevel();
                continueCount = 1;
            }
            if (continueCount > LevelRule.getPlayerNumber(currentLevel)) {
                players.remove(i);
                i--;
            }
        }

    }

    private void showPlayerLevelList(List<Player> players) {

        List<PlayerLevelListAdapter.Item> items = new ArrayList<>();
        for (Player player : players) {
            items.add(new PlayerLevelListAdapter.Item(
                    LevelRule.getLevelString(player.getLevel()),
                    player.getPlayerName(),
                    player.getCreatedAt()
            ));
        }
        PlayerLevelListAdapter adapter = new PlayerLevelListAdapter(this, items, P.getPlayerName());
        lvPlayerLevelList.setAdapter(adapter);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}

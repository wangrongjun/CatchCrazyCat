package com.wang.catchcrazycat.activity;

import android.app.Activity;

import com.wang.android_lib.util.NotificationUtil;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.game.Player;
import com.wang.java_util.GsonUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class PlayerLevelListActivity extends Activity {

    private void query() {

        BmobQuery<Player> query = new BmobQuery<>();
        query.setLimit(100);
        query.order("-level,-updateTime");
        query.addWhereGreaterThan("level", LevelRule.LEVEL_斗王);
        query.findObjects(new FindListener<Player>() {
            @Override
            public void done(List<Player> list, BmobException e) {
                String content;
                if (e == null) {
                    content = GsonUtil.formatJson(list);
                } else {
                    content = e.toString();
                }
                NotificationUtil.showNotification(PlayerLevelListActivity.this, 1, "Log", content);
            }
        });

    }

}

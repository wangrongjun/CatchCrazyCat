package com.wang.catchcrazycat.game;

import cn.bmob.v3.BmobObject;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class Player extends BmobObject {

    private String playerName;
    private Integer level;

    public Player(String playerObjectId) {
        setObjectId(playerObjectId);
    }

    public Player(String playerName, int level) {
        this.playerName = playerName;
        this.level = level;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}

package com.wang.catchcrazycat.bean;

import cn.bmob.v3.BmobObject;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class Player extends BmobObject {

    public static final int LEVEL_斗之气初级 = 1;//block=50
    public static final int LEVEL_斗之气中级 = 2;//block=40
    public static final int LEVEL_斗之气高级 = 3;//block=35
    public static final int LEVEL_斗者 = 4;//block=30
    public static final int LEVEL_斗师 = 5;//block=25
    public static final int LEVEL_大斗师 = 6;//block=20
    public static final int LEVEL_斗灵 = 7;//block=15
    public static final int LEVEL_斗王 = 8;//block=10
    public static final int LEVEL_斗皇 = 9;//block=5             有9名
    public static final int LEVEL_斗宗 = 10;//block=0            有7名
    public static final int LEVEL_斗尊 = 11;//block=0  70步以内  有5名
    public static final int LEVEL_斗圣 = 12;//block=0，50步以内  有3名
    public static final int LEVEL_斗帝 = 13;//block=0，25步以内  有1名

    private String playerName;
    private int level;

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

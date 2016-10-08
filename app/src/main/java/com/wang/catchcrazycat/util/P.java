package com.wang.catchcrazycat.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wang.android_lib.util.PrefUtil;
import com.wang.catchcrazycat.game.LevelRule;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class P extends PrefUtil {

    private static final String prefName = "catchCrazyCat";
    public static Context context;

    public static void setPlayerName(String playerName) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putString("playerName", playerName).apply();
    }

    public static String getPlayerName() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getString("playerName", "");
    }

    public static void setPlayerMaxLevel(int playerMaxLevel) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putInt("playerMaxLevel", playerMaxLevel).apply();
    }

    /**
     * 玩家已经成功通过的最高等级
     */
    public static int getPlayerMaxLevel() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getInt("playerMaxLevel", LevelRule.getNoneLevel());
    }

    /**
     * 删除该玩家记录时有用
     */
    public static void setPlayerObjectId(String playerObjectId) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putString("playerObjectId", playerObjectId).apply();
    }

    public static String getPlayerObjectId() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getString("playerObjectId", null);
    }

    public static void clear() {
        clear(context, prefName);
    }

}

package com.wang.catchcrazycat.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wang.android_lib.util.PrefUtil;
import com.wang.catchcrazycat.bean.Player;

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

    public static void setMaxLevel(int maxLevel) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putInt("maxLevel", maxLevel).apply();
    }

    public static int getMaxLevel() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getInt("maxLevel", Player.LEVEL_斗之气初级);
    }

}

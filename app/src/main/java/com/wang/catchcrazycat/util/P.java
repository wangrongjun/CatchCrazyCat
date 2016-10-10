package com.wang.catchcrazycat.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wang.android_lib.util.PrefUtil;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.java_program.app_upgrade.bean.AppLatestVersion;
import com.wang.java_util.TextUtil;

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

    /**
     * 设置是否可以升级。升级策略：
     * <p/>
     * 首先在欢迎页面查询最新版本，如果最新版本大于当前版本，则说明可以升级，canUpgrade设置为true。
     * 如果最新版本等于当前版本或者联网查询失败，canUpgrade设置为false。
     * <p/>
     * 2秒之后打开MainActivity后马上调用getCanUpgrade检测是否为true，若为true，弹出升级对话框。
     * 无论用户点击了升级对话框的取消还是升级按钮，都把canUpgrade设置为false。
     * <p/>
     * 保险起见，WelcomeActivity开始查询之前先设置为不可升级并清空Pref的version数据
     */
    public static void setCanUpgrade(boolean canUpgrade) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putBoolean("canUpgrade", canUpgrade).apply();
    }

    public static boolean getCanUpgrade() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getBoolean("canUpgrade", false);
    }

    public static AppLatestVersion getLatestVersion() {
        AppLatestVersion latestVersion = getEntity(context, prefName, AppLatestVersion.class);
        if (TextUtil.isEmpty(latestVersion.getApkFileUrl())) {
            return null;
        } else {
            return latestVersion;
        }
    }

    public static void setLatestVersion(AppLatestVersion latestVersion) {
        if (latestVersion == null) {
            latestVersion = new AppLatestVersion();
        }
        setEntity(context, prefName, latestVersion);
    }

}

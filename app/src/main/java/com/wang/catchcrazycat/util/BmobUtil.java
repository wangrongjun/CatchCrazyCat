package com.wang.catchcrazycat.util;

import android.content.Context;

import com.wang.android_lib.util.M;
import com.wang.catchcrazycat.game.Player;
import com.wang.java_util.TextUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * by Administrator on 2016/3/8.
 */
public class BmobUtil {

    public static final int BMOB_ERROR_CODE_NO_INTERNET = 9016;
    public static final int BMOB_ERROR_CODE_UNKNOWN = 9015;
    /**
     * 记录已存在（设置了唯一键）
     */
    public static final int BMOB_ERROR_CODE_DUPLICATE = 401;
    /**
     * 查询的 对象或Class 不存在 或者 登录接口的用户名或密码不正确
     */
    public static final int BMOB_ERROR_OBJECT_NOT_FOUND = 101;

    /**
     * 该方法以后会有很大用处，比如正式运营时不能显示英文，可以转化为中文。
     */
    public static String getExceptionHint(String msg, BmobException e) {
        String s;
        switch (e.getErrorCode()) {
            case BMOB_ERROR_CODE_DUPLICATE:
                s = "已存在";
                break;
            case BMOB_ERROR_CODE_NO_INTERNET:
                s = "请检测网络连接";
                break;
            case BMOB_ERROR_CODE_UNKNOWN:
                s = "未知异常";
                break;
            default:
                return msg + "   " + e.toString();
        }
        return msg + "，" + s;
    }

    public static void startDeleteOldIfExistsAndUploadLevel(final Context context,
                                                            final String playerName,
                                                            final int level) {
        //先删除所该玩家的记录，再插入包含该玩家名以及最高等级的记录（防止一个玩家占据多个等级的情况）
        String playerObjectId = P.getPlayerObjectId();
        if (!TextUtil.isEmpty(playerObjectId)) {
            new Player(playerObjectId).delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        startUploadLevel(context, playerName, level);
                    } else {
                        String hint = BmobUtil.getExceptionHint("上传成绩失败", e);
                        M.t(context, hint);
                        Util.showNotification(context, hint);
                    }
                }
            });
        } else {
            startUploadLevel(context, playerName, level);
        }

    }

    private static void startUploadLevel(final Context context, String playerName, int level) {
        new Player(playerName, level).save(new SaveListener<String>() {
            @Override
            public void done(String playerObjectId, BmobException e) {
                P.setPlayerObjectId(playerObjectId);
                if (e == null) {
                    M.t(context, "等级已上传，您可以查看封神榜的排名");
                } else {
                    String hint = BmobUtil.getExceptionHint("上传成绩失败了", e);
                    M.t(context, hint);
                    Util.showNotification(context, hint);
                }
            }
        });
    }

}

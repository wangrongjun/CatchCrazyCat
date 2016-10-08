package com.wang.catchcrazycat.game;

/**
 * by 王荣俊 on 2016/10/7.
 */
public class LevelRule {

    public static final int LEVEL_无 = 0;
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

    public static int getNoneLevel() {
        return LEVEL_无;
    }

    public static int getMinLevel() {
        return LEVEL_斗之气初级;
    }

    public static int getMaxLevel() {
        return LEVEL_斗帝;
    }

    //TODO 测试用，之后删除
    public static int getBlockNumber(int level) {
        return 70;
    }

    /**
     * @return 一开始随机出现的路障数目
     */
    public static int getBlockNumber111(int level) {
        switch (level) {
            case LevelRule.LEVEL_斗之气初级:
                return 50;
            case LevelRule.LEVEL_斗之气中级:
                return 40;
            case LevelRule.LEVEL_斗之气高级:
                return 35;
            case LevelRule.LEVEL_斗者:
                return 30;
            case LevelRule.LEVEL_斗师:
                return 25;
            case LevelRule.LEVEL_大斗师:
                return 20;
            case LevelRule.LEVEL_斗灵:
                return 15;
            case LevelRule.LEVEL_斗王:
                return 10;
            case LevelRule.LEVEL_斗皇:
                return 5;
            case LevelRule.LEVEL_斗宗:
            case LevelRule.LEVEL_斗尊:
            case LevelRule.LEVEL_斗圣:
            case LevelRule.LEVEL_斗帝:
                return 0;
        }
        return 50;
    }

    /**
     * 获取某个等级最大可以走的步数
     */
    public static int getMaxStep(int level) {
        switch (level) {
            case LevelRule.LEVEL_斗尊:
                return 50;
            case LevelRule.LEVEL_斗圣:
                return 30;
            case LevelRule.LEVEL_斗帝:
                return 20;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public static String getLevelString(int level) {
        switch (level) {
            case LevelRule.LEVEL_无:
                return "无";
            case LevelRule.LEVEL_斗之气初级:
                return "斗之气初级";
            case LevelRule.LEVEL_斗之气中级:
                return "斗之气中级";
            case LevelRule.LEVEL_斗之气高级:
                return "斗之气高级";
            case LevelRule.LEVEL_斗者:
                return "斗者";
            case LevelRule.LEVEL_斗师:
                return "斗师";
            case LevelRule.LEVEL_大斗师:
                return "大斗师";
            case LevelRule.LEVEL_斗灵:
                return "斗灵";
            case LevelRule.LEVEL_斗王:
                return "斗王";
            case LevelRule.LEVEL_斗皇:
                return "斗皇";
            case LevelRule.LEVEL_斗宗:
                return "斗宗";
            case LevelRule.LEVEL_斗尊:
                return "斗尊";
            case LevelRule.LEVEL_斗圣:
                return "斗圣";
            case LevelRule.LEVEL_斗帝:
                return "斗帝";
            default:
                return "error";
        }
    }

    /**
     * 获取每个等级的说明
     */
    public static String getLevelDescription(int level) {
        String levelDescription = "初始路障" + LevelRule.getBlockNumber(level) + "个";
        if (level >= LevelRule.LEVEL_斗尊) {
            int maxStep = LevelRule.getMaxStep(level);
            levelDescription += ",步数限制" + maxStep + "步";
        }
        return levelDescription;
    }

    /**
     * 获取可以登榜的最低等级
     */
    public static int getShowPlayerListMinLevel() {
        return LevelRule.LEVEL_斗皇;
    }

    /**
     * 获取每个等级会在封神榜上显示的玩家数量
     */
    public static int getPlayerNumber(int level) {
        switch (level) {
            case LevelRule.LEVEL_斗帝:
                return 1;
            case LevelRule.LEVEL_斗圣:
                return 3;
            case LevelRule.LEVEL_斗尊:
                return 5;
            case LevelRule.LEVEL_斗宗:
                return 7;
            case LevelRule.LEVEL_斗皇:
                return 9;
            default:
                return 0;
        }
    }

}

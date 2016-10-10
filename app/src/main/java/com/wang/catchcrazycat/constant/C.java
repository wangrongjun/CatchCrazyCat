package com.wang.catchcrazycat.constant;

/**
 * by 王荣俊 on 2016/10/9.
 */
public class C {

    public static final String hostHrl = "http://wangrongjun.cn/";

    public static String queryLatestVersionUrl() {
        return hostHrl + "AppUpgrade/queryLatest?appName=CatchCrazyCat";
    }

    public static String sourseCodeUrl() {
        return "https://github.com/wangrongjun/CatchCrazyCat";
    }

}

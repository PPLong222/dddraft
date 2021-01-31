package com.example.zyl.model;



import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;



//import cn.jpush.android.api.JPushInterface;


public class MyApplication extends LitePalApplication {

    public static final String TAG = "-----------";
    public static final String TAG2 = "++++++++++";
    public static final String TAG_finger = "finger-----------";
    public static String imgPath;//拍照的img路径

    public static String curUser;

    //单例模式获取MyApplication

    @Override
    public void onCreate() {
        super.onCreate();

    }





}

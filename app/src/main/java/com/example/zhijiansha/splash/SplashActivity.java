/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.splash;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhijiansha.myapplication.MainActivity;
import com.example.zhijiansha.myapplication.R;

/**
 * 欢迎页
 * Created by zhijiansha on 2017-10-21.
 */

public class SplashActivity extends Activity {
    /**
     * 是否第一次启动app标记
     *
     * @author zhijiansha
     * @time 2017-10-27 22:32
     */
    private boolean isFirstUse = true;
    /**
     * 数据共享 写入 读取 标记
     *
     * @author zhijiansha
     * @time 2017-10-27 22:33
     */
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    /**
     * timer 倒计时
     *
     * @author zhijiansha
     * @time 2017-10-27 22:34
     */
    private TextView mTVTime, mTVSplash;
    private RelativeLayout mSplashLayout;
    private int recLen = 5;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_splash);
        RelativeLayout mLayout = findViewById(R.id.splash_layout);
        mLayout.setBackgroundColor(getResources().getColor(R.color.C));
        preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        editor = preferences.edit();
        //初始化UI
        //判断是否首次安装启动
        if (isFirstUse) {
            initView();
            //倒计时5s关闭欢迎页
            handler.postDelayed(runnable, 1000);
        } else {
            //直接跳转到MainActivity
            startMainPage();
        }
    }

    /**
     * 初始化视图
     * 执行线程实现timer计时 自动退出欢迎页
     *
     * @author zhijiansha
     * @time 2017-10-27 22:35
     */
    public void initView() {
        mSplashLayout = findViewById(R.id.splash_layout);
        mTVTime = findViewById(R.id.tv_time);
        mTVSplash = findViewById(R.id.tv_splash);
        final int[] colorID = {R.color.A, R.color.B, R.color.C, R.color.D, R.color.E};
        editor.putBoolean("isFirstUse", false);
        editor.commit();// 保存新数据
        //点击倒计时退出splash
        mTVTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recLen = 0;
                Log.i("liutao", "=====startMainPage()====mTVTime==");
            }
        });
        //点击btn退出splash
        mTVSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recLen = 0;
                Log.i("liutao", "=====startMainPage()====mTVSplash==");
            }
        });
        //右上角 倒计时
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //判断recLen是否>0进行处理跳转（>0执行倒计时否则直接启动MainActivity）
                if (recLen > 0) {
                    Log.i("liutao", "=========runnable==" + recLen);
                    mTVTime.setText((recLen - 1) + "S");
                    //setBackgroundColor旧的写法
                    mSplashLayout.setBackgroundColor(getResources().getColor(colorID[recLen - 1]));
                    //setBackgroundColor新的写法(android L 报错)
                    //mSplashLayout.setBackgroundColor(getColor(colorID[recLen - 1]));
                    handler.postDelayed(this, 1000);
                    recLen--;
                } else {
                    Log.i("liutao", "=====startMainPage()====runnable==" + recLen);
                    startMainPage();
                }
            }
        };
    }

    /**
     * Intent 跳转到 MainActivity
     */
    public void startMainPage() {
        Log.i("liutao", "=====startMainPage()======");
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }
}

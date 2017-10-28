/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.zhijiansha.myapplication.common.PlayerManager;

/**
 * 视频播放界面
 *
 *
 *
 * Created by zhijiansha on 2017-10-21.
 */


public class VideoPlayerActivity extends AppCompatActivity implements PlayerManager.PlayerStateListener {
    private PlayerManager player;

    private Uri mFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        mFilePath= intent.getData();
        Log.i("liutao","=====URI INTENT===="+mFilePath.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        initPlayer(mFilePath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    private void initPlayer(Uri filePath) {
        player = new PlayerManager(this);
        player.setFullScreenOnly(true);
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        player.playInFullScreen(true);

        player.setPlayerStateListener(this);

        player.play(filePath);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {
        Toast.makeText(this,"ERROR PLAYER!!",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPlay() {

    }

}

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

import com.example.zhijiansha.widget.media.AndroidMediaController;
import com.example.zhijiansha.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 视频播放界面
 * <p>
 * <p>
 * <p>
 * Created by zhijiansha on 2017-10-21.
 */


public class VideoPlayerActivity extends AppCompatActivity {
    //private PlayerManager player;
    private Uri mFilePath;

    private AndroidMediaController mMediaController;
    private IjkVideoView mVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        mFilePath = intent.getData();

        mMediaController = new AndroidMediaController(this, false);
        //mMediaController.setSupportActionBar(null);
        Log.i("liutao", "=====URI INTENT====" + mFilePath.toString());

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(mFilePath);
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mVideoView.start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //initPlayer(mFilePath);
        //mVideoView.showMediaInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //player.stop();
    }
/*
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

    }*/

}

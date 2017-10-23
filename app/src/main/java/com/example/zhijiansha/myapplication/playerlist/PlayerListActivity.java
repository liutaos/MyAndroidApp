/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication.playerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.zhijiansha.Entity.Audio;
import com.example.zhijiansha.Entity.Video;
import com.example.zhijiansha.myapplication.Adapter.MusicAudioPlayerListAdapter;
import com.example.zhijiansha.myapplication.Adapter.VideoPlayerListAdapter;
import com.example.zhijiansha.myapplication.R;
import com.example.zhijiansha.tools.AudioProvider;
import com.example.zhijiansha.tools.VideoProvider;

import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends AppCompatActivity {
    private ListView mPlayerList;
    private MusicAudioPlayerListAdapter mPlayerAdapter;
    private VideoPlayerListAdapter mVideoPlayerAdapter;
    private AudioProvider mAudioProvider;
    private VideoProvider mVideoProvider;
    private List<Audio> mAudio = new ArrayList<>();
    private List<Video> mVideo = new ArrayList<>();

    private Intent mIntent;

    private static final String mActionAudio = "AUDIO";
    private static final String mActionMusic = "MUSIC";
    private static final String mActionVideo = "VIDEO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mIntent = this.getIntent();
        initData();
        initView();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new PlayerListActivityFragment()).commit();
        }
    }

    public void initView() {

        mPlayerList = (ListView) findViewById(R.id.player_list);
        if (mIntent.getAction().equals(mActionVideo)) {
            Log.i("liutao", "====initView: "+mActionVideo);
            mVideoPlayerAdapter = new VideoPlayerListAdapter(this, mVideo);
            mPlayerList.setAdapter(mVideoPlayerAdapter);
        } else {
            mPlayerAdapter = new MusicAudioPlayerListAdapter(this, mAudio);
            mPlayerList.setAdapter(mPlayerAdapter);
        }
    }

    public void initData() {

        if (mIntent.getAction().equals(mActionVideo)) {
            mVideoProvider = new VideoProvider(this);
            mVideo = mVideoProvider.getList();
            Log.i("liutao", "====initdata()====" + mVideoProvider.getList().size());
        } else {
            if (mIntent.getAction().equals(mActionAudio)) {
                mAudioProvider = new AudioProvider(this, mActionAudio);
            } else if(mIntent.getAction().equals(mActionMusic)) {
                mAudioProvider = new AudioProvider(this, mActionMusic);
            }
            mAudio = mAudioProvider.getList();
            Log.i("liutao", "====initdata()====" + mAudioProvider.getList().size());
        }
    }
}

/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication.playerlist;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhijiansha.Entity.Audio;
import com.example.zhijiansha.Entity.Image;
import com.example.zhijiansha.Entity.Video;
import com.example.zhijiansha.myapplication.Adapter.ImageListAdapter;
import com.example.zhijiansha.myapplication.Adapter.MusicAudioPlayerListAdapter;
import com.example.zhijiansha.myapplication.Adapter.VideoPlayerListAdapter;
import com.example.zhijiansha.myapplication.R;
import com.example.zhijiansha.tools.AudioProvider;
import com.example.zhijiansha.tools.ImageProvider;
import com.example.zhijiansha.tools.PermissionsChecker;
import com.example.zhijiansha.tools.VideoProvider;

import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends AppCompatActivity {
    private ListView mPlayerList;

    private ImageListAdapter mImageAdapter;
    private MusicAudioPlayerListAdapter mPlayerAdapter;
    private VideoPlayerListAdapter mVideoPlayerAdapter;

    private ImageProvider mImageProvider;
    private AudioProvider mAudioProvider;
    private VideoProvider mVideoProvider;

    private List<Image> mImage = new ArrayList<>();
    private List<Audio> mAudio = new ArrayList<>();
    private List<Video> mVideo = new ArrayList<>();

    private Intent mIntent;

    private static final String mActionImage = "IMAGE";
    private static final String mActionAudio = "AUDIO";
    private static final String mActionMusic = "MUSIC";
    private static final String mActionVideo = "VIDEO";

    private PermissionsChecker mPermissionsChecker;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)){
            Toast.makeText(this,"请赋予权限后启动！！",Toast.LENGTH_LONG).show();
            this.finish();

        }
        setContentView(R.layout.activity_player_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIntent = this.getIntent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new PlayerListActivityFragment()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();

        mPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PlayerListActivity.this, "===========", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initView() {
        TextView titleTV = (TextView) findViewById(R.id.title_toolbar);
        if (mIntent.getAction().equals(mActionImage)) {
            titleTV.setText(this.getString(R.string.btn_image_text));
        }

        if (mIntent.getAction().equals(mActionAudio)) {
            titleTV.setText(this.getString(R.string.btn_audio_text));
        }

        if (mIntent.getAction().equals(mActionMusic)) {
            titleTV.setText(this.getString(R.string.btn_music_text));
        }

        if (mIntent.getAction().equals(mActionVideo)) {
            titleTV.setText(this.getString(R.string.btn_video_text));
        }

        mPlayerList = (ListView) findViewById(R.id.player_list);

        if (mIntent.getAction().equals(mActionImage)) {
            mImageAdapter = new ImageListAdapter(this, mImage);
            mPlayerList.setAdapter(mImageAdapter);
        }

        if (mIntent.getAction().equals(mActionAudio) || mIntent.getAction().equals(mActionMusic)) {
            mPlayerAdapter = new MusicAudioPlayerListAdapter(this, mAudio);
            mPlayerList.setAdapter(mPlayerAdapter);
        }
        if (mIntent.getAction().equals(mActionVideo)) {
            Log.i("liutao", "====initView: " + mActionVideo);
            mVideoPlayerAdapter = new VideoPlayerListAdapter(this, mVideo);
            mPlayerList.setAdapter(mVideoPlayerAdapter);
        }
    }

    public void initData() {
        if (mIntent.getAction().equals(mActionImage)) {
            mImageProvider = new ImageProvider(this);
            mImage = mImageProvider.getList();
            Log.i("liutao", "====initdata()====" + mImageProvider.getList().size());
        }
        // if (mIntent.getAction().equals(mActionMusic)) {
        if (mIntent.getAction().equals(mActionAudio)) {
            mAudioProvider = new AudioProvider(this, mActionAudio);
            mAudio = mAudioProvider.getList();
            Log.i("liutao", "====initdata()====" + mAudioProvider.getList().size());
        }
        if (mIntent.getAction().equals(mActionMusic)) {
            mAudioProvider = new AudioProvider(this, mActionMusic);
            mAudio = mAudioProvider.getList();
            Log.i("liutao", "====initdata()====" + mAudioProvider.getList().size());
        }

        //}

        if (mIntent.getAction().equals(mActionVideo)) {
            mVideoProvider = new VideoProvider(this);
            mVideo = mVideoProvider.getList();
            Log.i("liutao", "====initdata()====" + mVideoProvider.getList().size());
        }
    }
}

/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication.playerlist;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.zhijiansha.myapplication.VideoPlayerActivity;
import com.example.zhijiansha.tools.AudioProvider;
import com.example.zhijiansha.tools.FileUriTools;
import com.example.zhijiansha.tools.ImageProvider;
import com.example.zhijiansha.tools.PermissionsChecker;
import com.example.zhijiansha.tools.VideoProvider;
import com.example.zhijiansha.widget.MyView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 媒体列表界面
 *
 * @author zhijiansha
 * @time 2017-10-26 23:44
 */
public class PlayerListActivity extends AppCompatActivity {
    private ListView mPlayerList;
    /**
     * 图片列表适配器
     *
     * @author zhijiansha
     * @time 2017-10-24 19:42
     */
    private ImageListAdapter mImageAdapter;

    /**
     * 音乐或者铃声列表适配器
     *
     * @author zhijiansha
     * @time 2017-10-24 19:42
     */
    private MusicAudioPlayerListAdapter mPlayerAdapter;

    /**
     * 视频列表适配器
     *
     * @author zhijiansha
     * @time 2017-10-24 19:42
     */
    private VideoPlayerListAdapter mVideoPlayerAdapter;

    //相关的provider
    private ImageProvider mImageProvider;
    private AudioProvider mAudioProvider;
    private VideoProvider mVideoProvider;
    //相关的对象集合
    private List<Image> mImage = new ArrayList<>();
    private List<Audio> mAudio = new ArrayList<>();
    private List<Video> mVideo = new ArrayList<>();

    private Intent mIntent;
    //相关的ACTION
    private static final String mActionImage = "IMAGE";
    private static final String mActionAudio = "AUDIO";
    private static final String mActionMusic = "MUSIC";
    private static final String mActionVideo = "VIDEO";

    private PermissionsChecker mPermissionsChecker;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private DataTask mDataTask;
    private MyView mView;// = new MyView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //获取返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //不显示默认的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //api 23（6.0）以上 检测权限 并设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置状态栏颜色
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //检测权限
            mPermissionsChecker = new PermissionsChecker(this);
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                Toast.makeText(this, "请赋予权限后启动！！", Toast.LENGTH_LONG).show();
                this.finish();
            }
        }
        //api 21（5.0）以上 api 23(6.0)以下
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.DKGRAY);
        }

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.font_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIntent = this.getIntent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new PlayerListActivityFragment()).commit();
        }
        initDataAndView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //新的写法 item 点击事件
        mPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Uri fileUri;
                File mFile;
                switch (mIntent.getAction()) {
                    case mActionImage:
                        mFile = new File(mImage.get(position).getPath());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            fileUri = new FileUriTools().getFileUriTools(getApplicationContext(), mFile);
                            intent.setDataAndType(fileUri, "image/*");
                        } else {
                            intent.setDataAndType(Uri.fromFile(mFile), "image/*");
                        }
                        break;

                    case mActionAudio:
                    case mActionMusic:
                        mFile = new File(mAudio.get(position).getPath());
                        intent = new Intent();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            fileUri = new FileUriTools().getFileUriTools(getApplicationContext(), mFile);
                            intent.setData(fileUri);
                        } else {
                            intent.setData(Uri.fromFile(mFile));
                        }
                        intent.setClass(getApplicationContext(), VideoPlayerActivity.class);
                        break;

                    case mActionVideo:
                        mFile = new File(mVideo.get(position).getPath());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            fileUri = new FileUriTools().getFileUriTools(getApplicationContext(), mFile);
                            intent.setData(fileUri);
                        } else {
                            intent.setData(Uri.fromFile(mFile));
                        }
                        intent.setClass(getApplicationContext(), VideoPlayerActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }


    /**
     * 初始化数据
     * and
     * 初始化视图
     *
     * @author zhijiansha
     * @time 2017-10-24 19:37
     */
    public void initDataAndView() {
        TextView titleTV = (TextView) findViewById(R.id.title_toolbar);
        mView = new MyView(this);
        mView = (MyView) findViewById(R.id.ani_myview);
        mPlayerList = (ListView) findViewById(R.id.player_list);

        switch (mIntent.getAction()) {

            case mActionImage:
                titleTV.setText(this.getString(R.string.btn_image_text));
                break;

            case mActionAudio:
                titleTV.setText(this.getString(R.string.btn_audio_text));
                break;

            case mActionMusic:
                titleTV.setText(this.getString(R.string.btn_music_text));
            case mActionVideo:
                titleTV.setText(this.getString(R.string.btn_video_text));
                break;

        }
        mDataTask = new DataTask();
        mDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /**
     * 初始化数据
     *
     * @author zhijiansha
     * @time 2017-10-24 19:37
     */
    class DataTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //加载动画
            mView.setDistance(100);
            mView.setVisibility(View.VISIBLE);
            //Toast.makeText(PlayerListActivity.this, "正在加载数据请稍候。。。。。。", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mView.setVisibility(View.GONE);
            switch (mIntent.getAction()) {

                case mActionImage:
                    mImageAdapter = new ImageListAdapter(PlayerListActivity.this, mImage);
                    mPlayerList.setAdapter(mImageAdapter);
                    break;

                case mActionAudio:
                    mPlayerAdapter = new MusicAudioPlayerListAdapter(PlayerListActivity.this, mAudio);
                    mPlayerList.setAdapter(mPlayerAdapter);
                    break;

                case mActionMusic:
                    mPlayerAdapter = new MusicAudioPlayerListAdapter(PlayerListActivity.this, mAudio);
                    mPlayerList.setAdapter(mPlayerAdapter);
                    break;
                case mActionVideo:
                    mVideoPlayerAdapter = new VideoPlayerListAdapter(PlayerListActivity.this, mVideo);
                    mPlayerList.setAdapter(mVideoPlayerAdapter);
                    break;
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (mIntent.getAction()) {

                case mActionImage:
                    mImageProvider = new ImageProvider(PlayerListActivity.this);
                    mImage = mImageProvider.getList();
                    break;

                case mActionAudio:
                    mAudioProvider = new AudioProvider(PlayerListActivity.this, mActionAudio);
                    mAudio = mAudioProvider.getList();
                    break;

                case mActionMusic:
                    mAudioProvider = new AudioProvider(PlayerListActivity.this, mActionMusic);
                    mAudio = mAudioProvider.getList();
                    break;
                case mActionVideo:
                    mVideoProvider = new VideoProvider(PlayerListActivity.this);
                    mVideo = mVideoProvider.getList();
                    break;
            }
            return null;
        }
    }
}

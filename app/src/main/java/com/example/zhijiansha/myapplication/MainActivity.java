package com.example.zhijiansha.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhijiansha.myapplication.about.AboutActivity;
import com.example.zhijiansha.myapplication.playerlist.PlayerListActivity;
import com.example.zhijiansha.tools.PermissionsChecker;

import java.util.Random;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends Activity implements View.OnClickListener {
    private Button mBtnImage, mBtnMusic, mBtnVideo, mBtnAudio, mBtnBook, mBtnSetings, mBtnAppInfo, mBtnAbout;
    private PermissionsChecker mPermissionsChecker;

    // 所需的全部权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            mPermissionsChecker = new PermissionsChecker(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= 23 && mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            MainActivityPermissionsDispatcher.readExtrnalWithPermissionCheck(this);
        }
        initView();
        resumeView();
    }


    public void initView() {
        mBtnImage = findViewById(R.id.btn_image);
        mBtnMusic = findViewById(R.id.btn_music);
        mBtnVideo = findViewById(R.id.btn_video);
        mBtnAudio = findViewById(R.id.btn_audio);
        mBtnBook = findViewById(R.id.btn_book);
        mBtnSetings = findViewById(R.id.btn_settings);
        mBtnAppInfo = findViewById(R.id.btn_appinfo);
        mBtnAbout = findViewById(R.id.btn_about);

        mBtnImage.setOnClickListener(this);
        mBtnMusic.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnAudio.setOnClickListener(this);
        mBtnBook.setOnClickListener(this);
        mBtnSetings.setOnClickListener(this);
        mBtnAppInfo.setOnClickListener(this);
        mBtnAbout.setOnClickListener(this);
    }

    /**
     * 设置 随机颜色值
     */
    public void resumeView() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        mBtnImage.setTextColor(Color.rgb(b, r, g));
        mBtnImage.setBackgroundColor(Color.rgb(r, g, b));

        mBtnMusic.setTextColor(Color.rgb(r, g, b));
        mBtnMusic.setBackgroundColor(Color.rgb(b, r, g));

        mBtnVideo.setTextColor(Color.rgb(b, g, r));
        mBtnVideo.setBackgroundColor(Color.rgb(r, b, g));

        mBtnAudio.setTextColor(Color.rgb(r, b, g));
        mBtnAudio.setBackgroundColor(Color.rgb(b, g, r));

        mBtnBook.setTextColor(Color.rgb(b, r, g));
        mBtnBook.setBackgroundColor(Color.rgb(r, g, b));

        mBtnSetings.setTextColor(Color.rgb(r, g, b));
        mBtnSetings.setBackgroundColor(Color.rgb(b, r, g));

        mBtnAppInfo.setTextColor(Color.rgb(b, g, r));
        mBtnAppInfo.setBackgroundColor(Color.rgb(r, b, g));

        mBtnAbout.setTextColor(Color.rgb(r, b, g));
        mBtnAbout.setBackgroundColor(Color.rgb(b, g, r));

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_image:
                intent.setClass(MainActivity.this, PlayerListActivity.class);
                intent.setAction("IMAGE");
                startActivity(intent);
                break;
            case R.id.btn_music:
                intent.setClass(MainActivity.this, PlayerListActivity.class);
                intent.setAction("MUSIC");
                startActivity(intent);
                break;
            case R.id.btn_video:
                intent.setClass(MainActivity.this, PlayerListActivity.class);
                intent.setAction("VIDEO");
                startActivity(intent);
                break;
            case R.id.btn_audio:
                intent.setClass(MainActivity.this, PlayerListActivity.class);
                intent.setAction("AUDIO");
                startActivity(intent);
                break;
            case R.id.btn_book:
                break;
            case R.id.btn_settings:
                break;
            case R.id.btn_appinfo:
                break;
            case R.id.btn_about:
                intent.setClass(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }

    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, maxSdkVersion = 24)
    void readExtrnal() {
        Toast.makeText(this, "权限已获取到！！！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readShow(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage("").setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        });
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readDenied() {
        Toast.makeText(this, "拒绝授权", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readAgain() {
        Toast.makeText(this, "拒绝授权不在询问", Toast.LENGTH_LONG).show();
        this.finish();
    }

}

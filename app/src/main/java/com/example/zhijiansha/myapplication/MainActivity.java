package com.example.zhijiansha.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhijiansha.myapplication.about.AboutActivity;
import com.example.zhijiansha.tools.PermissionsChecker;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnImage, mBtnMusic, mBtnVideo, mBtnAudio, mBtnBook, mBtnSetings, mBtnAppInfo, mBtnAbout;
    private PermissionsChecker mPermissionsChecker;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPermissionsChecker = new PermissionsChecker(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //MainActivityPermissionsDispatcher.readExtrnalWithPermissionCheck(this);
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            MainActivityPermissionsDispatcher.readExtrnalWithPermissionCheck(this);
        }
    }

    public void initView() {
        mBtnImage = (Button) findViewById(R.id.btn_image);
        mBtnMusic = (Button) findViewById(R.id.btn_music);
        mBtnVideo = (Button) findViewById(R.id.btn_video);
        mBtnAudio = (Button) findViewById(R.id.btn_audio);
        mBtnBook = (Button) findViewById(R.id.btn_book);
        mBtnSetings = (Button) findViewById(R.id.btn_settings);
        mBtnAppInfo = (Button) findViewById(R.id.btn_appinfo);
        mBtnAbout = (Button) findViewById(R.id.btn_about);

        mBtnImage.setOnClickListener(this);
        mBtnMusic.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnAudio.setOnClickListener(this);
        mBtnBook.setOnClickListener(this);
        mBtnSetings.setOnClickListener(this);
        mBtnAppInfo.setOnClickListener(this);
        mBtnAbout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_image:
                intent.setClass(MainActivity.this, ActivityListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_music:
                break;
            case R.id.btn_video:
                //intent = new Intent();
                //intent.setClass(MainActivity.this, ActivityListActivity.class);
                //startActivity(intent);
                break;
            case R.id.btn_audio:
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


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readExtrnal() {
        Toast.makeText(this, "权限已获取到！！！", Toast.LENGTH_LONG).show();
    }


    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readShow(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage("").setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        });
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readDenied() {
        Toast.makeText(this, "拒绝授权", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readAskAgin() {
        Toast.makeText(this, "拒绝授权不在询问", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}

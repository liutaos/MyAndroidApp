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

import com.example.zhijiansha.tools.PermissionsChecker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLink;
    private Button mBtnClose;
    private Button mListViewBtn;
    private Socket socket;
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
        SocketServers();
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
        mBtnLink = (Button) findViewById(R.id.button_link);
        mBtnClose = (Button) findViewById(R.id.button_close);
        mListViewBtn = (Button) findViewById(R.id.btn_list);
        mBtnLink.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnLink.setVisibility(View.GONE);
        mBtnClose.setVisibility(View.GONE);
        mListViewBtn.setOnClickListener(this);

    }

    public void SocketServers() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //创建Socket
                try {
                    socket = new Socket("192.168.0.101", 80);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_link:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            //向服务器发送消息
                            //建立输出流
                            if (socket != null) {
                                OutputStream out = socket.getOutputStream();
                                out.write("发送消息".getBytes("utf-8"));
                                //关闭流
                                out.close();
                            } else {
                                Toast.makeText(MainActivity.this, "连接失败！！", Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.button_close:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            OutputStream out = socket.getOutputStream();
                            out.write("断开连接".getBytes("utf-8"));
                            out.close();
                            //关闭Socket
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.btn_list:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ActivityListActivity.class);
                startActivity(intent);
        }
    }


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readExtrnal() {
        //initView();
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

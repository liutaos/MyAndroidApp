package com.example.zhijiansha.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLink;
    private Button mBtnClose;
    private Button mListViewBtn;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SocketServers();
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
}

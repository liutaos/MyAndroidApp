/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.zhijiansha.tools.Image;
import com.example.zhijiansha.tools.ImageProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhijiansha on 2017-10-21.
 */

public class ActivityListActivity extends AppCompatActivity {
    private ListView mLv;
    private ActivityListAdapter mAdapter;
    private ImageProvider mImageProvider = new ImageProvider(this);
    private List<Image> mImageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        mAdapter = new ActivityListAdapter(this, mImageList);
        mLv.setAdapter(mAdapter);
    }

    public void initView() {
        mLv = (ListView) findViewById(R.id.list_item);
    }

    public void initData() {
        mImageList = mImageProvider.getList();
    }

}

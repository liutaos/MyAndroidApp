/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zhijiansha.Entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhijiansha on 2017-10-21.
 */

public class ActivityListActivity extends Activity  {
    private ListView mLv;
    private ActivityListAdapter mAdapter;
    private Entity mEntity;
    private List<Entity> mEntityList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
    }

    public void initView(){
        mLv = findViewById(R.id.list_item);
        mAdapter = new ActivityListAdapter(this,mEntityList);
        mLv.setAdapter(mAdapter);
    }

    public void initData(){
        for (int i=0;i<1000;i++) {
            mEntity = new Entity();
            mEntity.setTvData("Test item "+(i+1));
            mEntityList.add(mEntity);
        }
    }

}

/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zhijiansha.myapplication.R;

/**
 * Created by zhijiansha on 2017-10-30.
 */

public class MySeekBarDialogView extends Dialog implements View.OnClickListener {
    private Context mContext;
    private Button mStopBtn, mCloseBtn;
    private SeekBar mSeekBar;
    private TextView mLeftTv, mRightTv;

    public MySeekBarDialogView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    public void create() {
        super.create();
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_dialog_layout, null);
        setContentView(view);

        mStopBtn = view.findViewById(R.id.stop_music_btn);
        mCloseBtn = view.findViewById(R.id.close_music_btn);
        mSeekBar = view.findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mLeftTv = view.findViewById(R.id.left_time);
        mRightTv = view.findViewById(R.id.right_time);


    }

    @Override
    public void onClick(View v) {

    }
}

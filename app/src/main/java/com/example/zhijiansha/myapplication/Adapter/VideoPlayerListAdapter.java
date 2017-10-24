/*
 * Copyright (c) 2017. 
 * liutao 
 * 版权所有
 */

package com.example.zhijiansha.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhijiansha.Entity.Video;
import com.example.zhijiansha.myapplication.R;
import com.example.zhijiansha.tools.FileUriTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayerListAdapter extends BaseAdapter {

    private List<Video> mVideo = new ArrayList<Video>();

    private Context context;
    private LayoutInflater layoutInflater;

    private MediaPlayer mMediaPlayer = new MediaPlayer();


    public VideoPlayerListAdapter(Context context , List<Video> video) {
        this.context = context;
        this.mVideo = video;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mVideo.size();
    }

    @Override
    public Video getItem(int position) {
        return mVideo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_player_list_layout, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Video object, ViewHolder holder) {
        //TODO implement
        final Video mVideo = object;
        holder.mHolderTv.setText(mVideo.getTitle());
        holder.mHolderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Uri videoUri;
                File videoFile = new File(mVideo.getPath());
                intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    videoUri=new FileUriTools().getFileUriTools(context,videoFile);
                    //intent.setDataAndType(videoUri, "video/*");
                    try {
                        mMediaPlayer.setDataSource(context, videoUri);
                        mMediaPlayer.seekTo(0);
                        mMediaPlayer.start();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(videoFile), "video/*");
                }
                //context.startActivity(intent);
            }
        });
    }

    protected class ViewHolder {
        public TextView mHolderTv;

        public ViewHolder(View view) {
            mHolderTv = view.findViewById(R.id.player_tv_title);
            mHolderTv.setTextColor(context.getResources().getColor(R.color.item_title_color));
        }
    }
}

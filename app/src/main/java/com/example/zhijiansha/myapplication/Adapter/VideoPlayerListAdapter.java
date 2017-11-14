/*
 * Copyright (c) 2017. 
 * liutao 
 * 版权所有
 */

package com.example.zhijiansha.myapplication.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhijiansha.Entity.Video;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * VIDEO 列表适配器
 *
 * @author zhijiansha
 * @time 2017-10-27 22:40
 */
public class VideoPlayerListAdapter extends BaseAdapter {

    private List<Video> mVideo = new ArrayList<Video>();

    private Context context;
    private LayoutInflater layoutInflater;

    private MediaPlayer mMediaPlayer = new MediaPlayer();


    public VideoPlayerListAdapter(Context context, List<Video> video) {
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
        holder.mHolderIv.setImageDrawable(context.getResources().getDrawable(R.drawable.videos_icon));
    }

    protected class ViewHolder {
        public TextView mHolderTv;

        public ImageView mHolderIv;

        public ViewHolder(View view) {
            mHolderTv = view.findViewById(R.id.player_tv_title);
            mHolderIv = view.findViewById(R.id.player_thumbnail);
            mHolderTv.setTextColor(context.getResources().getColor(R.color.item_title_color));
        }
    }
}

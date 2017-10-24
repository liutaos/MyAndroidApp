/*
 * Copyright (c) 2017. 
 * liutao 
 * 版权所有
 */

package com.example.zhijiansha.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhijiansha.Entity.Audio;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MusicAudioPlayerListAdapter extends BaseAdapter {

    private List<Audio> mAudio = new ArrayList<Audio>();

    private Context context;
    private LayoutInflater layoutInflater;

    public MusicAudioPlayerListAdapter(Context context , List<Audio> audio) {
        this.context = context;
        this.mAudio = audio;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAudio.size();
    }

    @Override
    public Audio getItem(int position) {
        return mAudio.get(position);
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

    private void initializeViews(Audio object, ViewHolder holder) {
        //TODO implement
        final Audio mAudio = object;
        holder.mHolderTv.setText(mAudio.getTitle());
    }

    protected class ViewHolder {
        public TextView mHolderTv;

        public ViewHolder(View view) {
            mHolderTv = view.findViewById(R.id.player_tv_title);
        }
    }
}

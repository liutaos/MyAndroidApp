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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhijiansha.Entity.Audio;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhijiansha.myapplication.R.mipmap.ic_launcher;

/**
 * MUSIC AUDIO 列表适配器
 *
 * @author zhijiansha
 * @time 2017-10-27 22:40
 */

public class MusicAudioPlayerListAdapter extends BaseAdapter {

    private List<Audio> mAudio = new ArrayList<Audio>();

    private Context context;
    private LayoutInflater layoutInflater;

    public MusicAudioPlayerListAdapter(Context context, List<Audio> audio) {
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
        holder.mHolderIv.setImageDrawable(context.getResources().getDrawable(ic_launcher));
        //旧的写法
        /*holder.mHolderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Uri musicUri;
                File audioFile = new File(mAudio.getPath());
                intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    musicUri = new FileUriTools().getFileUriTools(context, audioFile);
                    intent.setDataAndType(musicUri, "audio/*");
                } else {
                    intent.setDataAndType(Uri.fromFile(audioFile), "audio/*");
                }
                context.startActivity(intent);
            }
        });*/

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

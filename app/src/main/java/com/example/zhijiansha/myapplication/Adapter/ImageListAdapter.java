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

import com.example.zhijiansha.Entity.Image;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * IMAGE 列表适配器
 * Created by zhijiansha on 2017-10-21.
 */

public class ImageListAdapter extends BaseAdapter {

    private List<Image> mImage = new ArrayList<Image>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ImageListAdapter(Context context, List<Image> mImage) {
        this.context = context;
        this.mImage = mImage;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImage.size();
    }

    @Override
    public Object getItem(int position) {
        return mImage.get(position);
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

    private void initializeViews(Object object, ViewHolder holder) {
        //TODO implement
        final Image mImage = (Image) object;
        holder.mHolderTv.setText(mImage.getTitle());
        holder.mHolderIv.setImageBitmap(mImage.getThumbnail());
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

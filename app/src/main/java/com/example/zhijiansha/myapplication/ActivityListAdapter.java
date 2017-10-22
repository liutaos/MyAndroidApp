/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhijiansha.Entity.Entity;
import com.example.zhijiansha.tools.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhijiansha on 2017-10-21.
 */

public class ActivityListAdapter extends BaseAdapter {

    private List<Entity> mEntity = new ArrayList<Entity>();
    private List<Image> mImage = new ArrayList<Image>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ActivityListAdapter(Context context, List<Image> mImage) {
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
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        Log.i("liutao"," ====getView()==="+getItem(position));
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Object object, ViewHolder holder) {
        //TODO implement
        final Image mImage = (Image) object;
        Log.i("liutao","====initializeViews==="+mImage.getTitle());
        holder.mHolderTv.setText(mImage.getTitle());
        holder.mHolderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ITEM 响应 "+mImage.getSize(),Toast.LENGTH_LONG).show();
            }
        });
        holder.mImageView.setImageURI(Uri.fromFile(new File(mImage.getPath())));
    }

    protected class ViewHolder {

        public TextView mHolderTv;
        public ImageView mImageView;

        public ViewHolder(View view) {
            mHolderTv = view.findViewById(R.id.list_tv_item);
            mImageView = view.findViewById(R.id.item_icon);
        }
    }
}

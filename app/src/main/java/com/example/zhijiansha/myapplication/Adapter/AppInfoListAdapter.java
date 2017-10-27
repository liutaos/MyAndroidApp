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

import com.example.zhijiansha.Entity.AppInfo;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有APP列表适配器
 *
 * @author zhijiansha
 * @time 2017-10-27 22:39
 */
public class AppInfoListAdapter extends BaseAdapter {

    private List<AppInfo> mAppInfo = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public AppInfoListAdapter(Context context, List<AppInfo> appInfo) {
        this.context = context;
        this.mAppInfo = appInfo;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAppInfo.size();
    }

    @Override
    public AppInfo getItem(int position) {
        return mAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.appinfo_item_layout, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(AppInfo appInfo, ViewHolder holder) {
        //TODO implement
        final AppInfo mAppInfo = appInfo;
        holder.mImageView.setImageDrawable(mAppInfo.getAppIcon());
        holder.mTvAppLabel.setText(mAppInfo.getAppLabel());
        holder.mTvPkgName.setText(mAppInfo.getPkgName());
    }

    protected class ViewHolder {
        ImageView mImageView;
        TextView mTvLabel, mTvAppLabel, mTvName, mTvPkgName;

        public ViewHolder(View view) {
            mImageView = view.findViewById(R.id.imgApp);
            mTvLabel = view.findViewById(R.id.tvLabel);
            mTvAppLabel = view.findViewById(R.id.tvAppLabel);
            mTvName = view.findViewById(R.id.tvName);
            mTvPkgName = view.findViewById(R.id.tvPkgName);
        }
    }
}

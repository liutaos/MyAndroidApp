/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhijiansha.Entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhijiansha on 2017-10-21.
 */

public class ActivityListAdapter extends BaseAdapter {

    private List<Entity> mEntity = new ArrayList<Entity>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ActivityListAdapter(Context context, List<Entity> mEntity) {
        this.context = context;
        this.mEntity = mEntity;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mEntity.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntity.get(position);
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
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Object object, ViewHolder holder) {
        //TODO implement
        Entity mEntityobj = (Entity) object;
        holder.mHolderTv.setText(mEntityobj.getTvData());
    }

    protected class ViewHolder {

        public TextView mHolderTv;

        public ViewHolder(View view) {
            mHolderTv = view.findViewById(R.id.list_tv_item);
        }
    }
}

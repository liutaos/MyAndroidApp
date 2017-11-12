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
        //Drawable mDbl = new BitmapDrawable(mImage.getThumbnail());
        holder.mHolderIv.setImageBitmap(mImage.getThumbnail());
        //holder.mHolderIv.setImageDrawable(mDbl);
        /*holder.mHolderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Uri pictureUri;
                intent = new Intent();
                File pictureFile = new File(mImage.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pictureUri=new FileUriTools().getFileUriTools(context,pictureFile);
                    intent.setDataAndType(pictureUri, "image/*");
                } else {
                    intent.setDataAndType(Uri.fromFile(pictureFile), "image/*");
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

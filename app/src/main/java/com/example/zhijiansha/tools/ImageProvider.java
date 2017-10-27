
/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.tools;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.zhijiansha.Entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Image Provider
 *
 * @author 返回查询集合
 *         List<Audio>F
 * @time 2017-10-27 22:45
 */
public class ImageProvider implements AbstructProvider {
    private Context context;

    public ImageProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<Image> getList() {
        List<Image> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                    Image image = new Image(id, title, displayName, mimeType,
                            path, size);
                    list.add(image);
                }
                cursor.close();
            }
        }
        return list;
    }

}
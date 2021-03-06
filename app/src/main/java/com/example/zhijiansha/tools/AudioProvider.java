
/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.tools;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.zhijiansha.Entity.Audio;

import java.util.ArrayList;
import java.util.List;

/**
 * AUDIO Provider
 * 返回查询集合
 * List<Audio>F
 *
 * @author zhijiansha
 * @time 2017-10-27 22:43
 */
public class AudioProvider implements AbstructProvider {

    private Context context;
    /**
     * 筛选  音乐或者铃声
     *
     * @author zhijiansha
     * @time 2017-10-24 19:38
     */
    private static String mStr;

    public AudioProvider(Context context, String str) {
        this.context = context;
        mStr = str;

    }

    @Override
    public List<Audio> getList() {
        List<Audio> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<Audio>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String album = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                    long duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                    if ((duration / 1000 / 60) > 1 && mStr.equals("MUSIC")) {
                        Audio audio = new Audio(id, title, album, artist, path,
                                displayName, mimeType, duration, size);
                        list.add(audio);
                    }
                    if ((duration / 1000 / 60) < 1 && mStr.equals("AUDIO")) {
                        Audio audio = new Audio(id, title, album, artist, path,
                                displayName, mimeType, duration, size);
                        list.add(audio);
                    }
                }
                cursor.close();
            }
        }
        return list;
    }

}
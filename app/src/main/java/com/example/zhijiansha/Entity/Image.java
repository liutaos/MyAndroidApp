/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.Entity;

import android.graphics.Bitmap;

/**
 * IMAGE 实体类
 *
 * @author zhijiansha
 * @time 2017-10-27 22:41
 */
public class Image {
    private int id;
    private String title;
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private Bitmap thumbnail;

    public Image() {
        super();
    }

    /**
     * @param id
     * @param title
     * @param displayName
     * @param mimeType
     * @param path
     * @param size
     */
    public Image(int id, String title, String displayName, String mimeType,
                 String path, long size, Bitmap thumbnail) {
        super();
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public  void setThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail(){
        return thumbnail;
    }
}
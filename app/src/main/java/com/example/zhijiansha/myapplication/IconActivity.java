/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
/**
*图片查看界面
*@author zhijiansha
*@time 2017-11-14 17:09
*/
public class IconActivity extends AppCompatActivity {

    private PhotoView mPhotoView;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;
    private Uri mFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        Intent getIntent =this.getIntent();
        mFileUri = getIntent.getData();
        Log.i("liutao", "=====icon====getIntent()==" + mFileUri.toString());
        mPhotoView = new PhotoView(this);
        mAttacher = new PhotoViewAttacher(mPhotoView);
        mPhotoView = (PhotoView)findViewById(R.id.photo_View);
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mFileUri);
            mPhotoView.setImageBitmap(mBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAttacher.update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("liutao", "======onDestroy: ");
        mBitmap=null;
        mAttacher.cleanup();
    }
}

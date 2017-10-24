/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.tools;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * ANDROID N 文件 URI 获取工具类
 * <p>
 * Created by zhijiansha on 2017-10-24.
 */

public class FileUriTools {
    /**
     * public 获取URI
     * 传递参数 arget{Context,File}
     *
     * @author zhijiansha
     * @time 2017-10-24 20:31
     */
    public Uri getFileUriTools(Context context, File file) {
        return fileUri(context, file);
    }

    /**
     * 封装 获取URI
     * 传递参数 arget{Context,File}
     *
     * @author zhijiansha
     * @time 2017-10-24 20:32
     */
    private Uri fileUri(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
    }
}

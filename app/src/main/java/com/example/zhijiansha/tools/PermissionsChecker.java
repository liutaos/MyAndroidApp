/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 权限判断类
 * 返回是否已获取到权限
 * boolean
 * Created by zhijiansha on 2017-10-22.
 */

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 判断权限集合
     * 返回 true or false
     *
     * @author zhijiansha
     * @time 2017-10-24 19:37
     */
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}

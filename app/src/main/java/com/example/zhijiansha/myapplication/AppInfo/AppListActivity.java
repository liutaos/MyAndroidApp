/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication.AppInfo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhijiansha.Entity.AppInfo;
import com.example.zhijiansha.myapplication.Adapter.AppInfoListAdapter;
import com.example.zhijiansha.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 显示所有APP列表界面
 *
 * @author zhijiansha
 * @time 2017-10-27 22:37
 */
public class AppListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView mlistview = null;
    /**
     * app 实体类
     *
     * @author zhijiansha
     * @time 2017-10-27 21:30
     */
    private List<AppInfo> mlistAppInfo = null;
    /**
     * app信息适配器
     *
     * @author zhijiansha
     * @time 2017-10-27 21:30
     */
    private AppInfoListAdapter mAppInfoListAdapter;
    /**
     * 包名管理器
     *
     * @author zhijiansha
     * @time 2017-10-27 21:29
     */
    private PackageManager mPackageManager;

    private String pkgName = null;

    private BroadcastReceiver mReceiver = null;

    private TextView mTvAppLabel, mTv_PkgName;

    private ArrayList<String> mPkgNameList = new ArrayList<String>();
    /**
     * 异步任务  加载所有APP信息
     *
     * @author zhijiansha
     * @time 2017-10-27 21:30
     */
    private QueryTask mQueryTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appinfo_toolbar);
        setSupportActionBar(toolbar);
        //获取返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //不显示默认的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.font_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTV = (TextView) findViewById(R.id.appinfo_title_toolbar);
        titleTV.setText(getResources().getString(R.string.btn_appinfo_text));
        mlistview = (ListView) findViewById(R.id.appinfo_listview);
        mlistAppInfo = new ArrayList<>();
        //queryAppInfo();
        mQueryTask = new QueryTask();
        mQueryTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAppInfoListAdapter = new AppInfoListAdapter(this, mlistAppInfo);
        mlistview.setAdapter(mAppInfoListAdapter);
        mlistview.setOnItemClickListener(this);
        mlistview.setOnItemLongClickListener(this);
        //注册广播 接收系统app更改信息
        mReceiver = new BroadcastReceiver() {
            @SuppressWarnings("deprecation")
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (Intent.ACTION_PACKAGE_ADDED.equals(action) ||
                        Intent.ACTION_PACKAGE_REMOVED.equals(action) ||
                        Intent.ACTION_PACKAGE_CHANGED.equals(action) ||
                        Intent.ACTION_PACKAGE_REPLACED.equals(action) ||
                        Intent.ACTION_PACKAGE_RESTARTED.equals(action) ||
                        Intent.ACTION_PACKAGE_INSTALL.equals(action)) {
                    queryAppInfo();
                    mAppInfoListAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "列表已刷新", Toast.LENGTH_LONG).show();
                }
            }
        };

        IntentFilter filters = new IntentFilter();

        filters.addDataScheme("package");

        filters.addAction(Intent.ACTION_PACKAGE_ADDED);

        filters.addAction(Intent.ACTION_PACKAGE_REMOVED);

        filters.addAction(Intent.ACTION_PACKAGE_REPLACED);

        registerReceiver(mReceiver, filters);


    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {
        // TODO Auto-generated method stub
        mTvAppLabel = view.findViewById(R.id.tvAppLabel);
        mTv_PkgName = view.findViewById(R.id.tvPkgName);

        Toast.makeText(this,
                "启动：" + mTvAppLabel.getText().toString(), Toast.LENGTH_LONG)
                .show();
        Intent intent = mlistAppInfo.get(position).getIntent();
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        // TODO 卸载
        mTv_PkgName = view.findViewById(R.id.tvPkgName);
        mTvAppLabel = view.findViewById(R.id.tvAppLabel);

        Toast.makeText(this,
                "卸载：" + mTvAppLabel.getText().toString(), Toast.LENGTH_LONG)
                .show();
        Log.i("liutao", "TV:" + mTv_PkgName.getText().toString());
        Uri packageURI = Uri
                .parse("package:" + mTv_PkgName.getText().toString());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);

        return true;
    }

    /**
     * 获得所有启动Activity的信息
     *
     * @author zhijiansha
     * @time 2017-10-27 21:27
     */
    public void queryAppInfo() {
        mPackageManager = this.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.获取所有app信息
        List<ResolveInfo> resolveInfos = mPackageManager
                .queryIntentActivities(mainIntent, PackageManager.MATCH_ALL);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos,
                new ResolveInfo.DisplayNameComparator(mPackageManager));
        if (mlistAppInfo != null) {
            mlistAppInfo.clear();
            for (ResolveInfo reInfo : resolveInfos) {
                pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
                try {
                    String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
                    String appLabel = (String) reInfo.loadLabel(mPackageManager); // 获得应用程序的Label
                    Drawable icon = reInfo.loadIcon(mPackageManager); // 获得应用程序图标
                    // 为应用程序的启动Activity 准备Intent
                    Intent launchIntent = new Intent();
                    launchIntent.setComponent(new ComponentName(pkgName,
                            activityName));
                    // 创建一个AppInfo对象，并赋值
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppLabel(appLabel);
                    appInfo.setPkgName(pkgName);
                    appInfo.setAppIcon(icon);
                    appInfo.setIntent(launchIntent);
                    mlistAppInfo.add(appInfo); // 添加至列表中
                    mPkgNameList.add(pkgName);
                    Log.i("liutao", "===  000 queryAppInfo()==" + mPkgNameList);
                } catch (Exception e) {
                    Log.e("liutao", "==NameNotFoundException===" + e);
                    System.out.print(e);
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * QueryTask
     * 异步加载所有APP信息
     *
     * @author zhijiansha
     * @time 2017-10-27 21:27
     */
    class QueryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAppInfoListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            queryAppInfo();
            return null;
        }
    }
}

/*
 * Copyright (c) 2017.
 * liutao
 * 版权所有
 */

package com.example.zhijiansha.myapplication.playerlist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhijiansha.myapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerListActivityFragment extends Fragment {
    private ListView mPlayerList;

    public PlayerListActivityFragment() {
       // mPlayerList = getView().findViewById(R.id.player_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_list, container, false);
    }
}

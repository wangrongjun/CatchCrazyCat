package com.wang.catchcrazycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wang.catchcrazycat.R;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class PlayerLevelListAdapter extends BaseAdapter {

    private Context context;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lv_player_level_list, parent, false);
    }
}

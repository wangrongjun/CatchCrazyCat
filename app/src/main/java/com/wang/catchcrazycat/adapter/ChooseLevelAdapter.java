package com.wang.catchcrazycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.android_lib.util.ResourceUtil;
import com.wang.catchcrazycat.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class ChooseLevelAdapter extends BaseAdapter {

    private Context context;
    private List<ListItem> items;

    public ChooseLevelAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getLevel();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_choose_level, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        updateView(viewHolder, position);
        return convertView;
    }

    private void updateView(ViewHolder viewHolder, int position) {
        ListItem item = items.get(position);
        viewHolder.tvLevelName.setText(item.getLevelString());
        viewHolder.tvLevelDescription.setText(item.getDescription());
        if (item.isClickable()) {
            viewHolder.tvLevelName.setTextColor(ResourceUtil.getColor(context, R.color.black));
        } else {
            viewHolder.tvLevelName.setTextColor(ResourceUtil.getColor(context, R.color.gray));
        }
    }

    public static class ListItem {
        private int level;
        private String levelString;
        private String description;
        private boolean clickable;

        public ListItem(int level, String levelString, String description, boolean clickable) {
            this.level = level;
            this.levelString = levelString;
            this.description = description;
            this.clickable = clickable;
        }

        public int getLevel() {
            return level;
        }

        public String getLevelString() {
            return levelString;
        }

        public String getDescription() {
            return description;
        }

        public boolean isClickable() {
            return clickable;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_level_name)
        TextView tvLevelName;
        @Bind(R.id.tv_level_description)
        TextView tvLevelDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

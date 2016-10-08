package com.wang.catchcrazycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.catchcrazycat.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class PlayerLevelListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> items;

    public PlayerLevelListAdapter(Context context, List<Item> items) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_player_level_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        updateView(viewHolder, position);
        return convertView;
    }

    private void updateView(ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.tvLevelName.setText(item.getLevelName());
        viewHolder.tvPlayerName.setText(item.getPlayerName());
        viewHolder.tvCreateTime.setText(item.getCreateTime());
    }

    static class ViewHolder {
        @Bind(R.id.tv_level_name)
        TextView tvLevelName;
        @Bind(R.id.tv_player_name)
        TextView tvPlayerName;
        @Bind(R.id.tv_create_time)
        TextView tvCreateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static class Item {
        private String levelName;
        private String playerName;
        private String createTime;

        public Item(String levelName, String playerName, String createTime) {
            this.levelName = levelName;
            this.playerName = playerName;
            this.createTime = createTime;
        }

        public String getLevelName() {
            return levelName;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getCreateTime() {
            return createTime;
        }
    }

}

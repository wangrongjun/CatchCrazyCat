package com.wang.catchcrazycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    private static final int TYPE_FIRST = 1;//第一项
    private static final int TYPE_NORMAL = 2;//其他项

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
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //TODO 使用不同的ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == TYPE_FIRST) {

            FirstViewHolder firstViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.lv_player_level_list_first, parent, false);
                firstViewHolder = new FirstViewHolder(convertView);
                convertView.setTag(firstViewHolder);
            } else {
                firstViewHolder = (FirstViewHolder) convertView.getTag();
            }
            updateFirstView(firstViewHolder, position);

        } else if (getItemViewType(position) == TYPE_NORMAL) {

            NormalViewHolder normalViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.lv_player_level_list_normal, parent, false);
                normalViewHolder = new NormalViewHolder(convertView);
                convertView.setTag(normalViewHolder);
            } else {
                normalViewHolder = (NormalViewHolder) convertView.getTag();
            }
            updateNormalView(normalViewHolder, position);

        }

        return convertView;
    }

    private void updateFirstView(FirstViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.tvPlayerName.setText(item.getPlayerName());
        viewHolder.tvCreateTime.setText(item.getCreateTime());
    }

    private void updateNormalView(NormalViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.tvLevelName.setText(item.getLevelName());
        viewHolder.tvPlayerName.setText(item.getPlayerName());
        viewHolder.tvCreateTime.setText(item.getCreateTime());
        position++;//等级从1排起
        setNumberImage(viewHolder.ivNumberOne, position / 10);
        setNumberImage(viewHolder.ivNumberTwo, position % 10);
    }

    private void setNumberImage(ImageView ivNumber, int number) {
        switch (number) {
            case 0:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_0);
                break;
            case 1:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_1);
                break;
            case 2:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_2);
                break;
            case 3:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_3);
                break;
            case 4:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_4);
                break;
            case 5:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_5);
                break;
            case 6:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_6);
                break;
            case 7:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_7);
                break;
            case 8:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_8);
                break;
            case 9:
                ivNumber.setImageResource(R.mipmap.ic_player_level_list_9);
                break;
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

    static class FirstViewHolder {
        @Bind(R.id.tv_player_name)
        TextView tvPlayerName;
        @Bind(R.id.tv_create_time)
        TextView tvCreateTime;

        FirstViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class NormalViewHolder {
        @Bind(R.id.iv_number_one)
        ImageView ivNumberOne;
        @Bind(R.id.iv_number_two)
        ImageView ivNumberTwo;
        @Bind(R.id.tv_level_name)
        TextView tvLevelName;
        @Bind(R.id.tv_player_name)
        TextView tvPlayerName;
        @Bind(R.id.tv_create_time)
        TextView tvCreateTime;

        NormalViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

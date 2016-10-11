package com.wang.catchcrazycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.android_lib.util.ResourceUtil;
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
    private String playerName;

    /**
     * 注意，如果getViewTypeCount返回了2，那么getItemViewType一定要返回0，1，否则一定数组越界出错。
     */
    private static final int TYPE_BEST = 0;//第1,2,3项
    private static final int TYPE_NORMAL = 1;//其他项

    public PlayerLevelListAdapter(Context context, List<Item> items, String playerName) {
        this.context = context;
        this.items = items;
        this.playerName = playerName;
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
        if (position <= 2) {
            return TYPE_BEST;
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

        if (getItemViewType(position) == TYPE_BEST) {

            BestViewHolder bestViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.lv_player_level_list_best, parent, false);
                bestViewHolder = new BestViewHolder(convertView);
                convertView.setTag(bestViewHolder);
            } else {
                bestViewHolder = (BestViewHolder) convertView.getTag();
            }
            updateBestView(bestViewHolder, position);

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

    private void updateBestView(BestViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.tvLevelName.setText(item.getLevelName());
        if (item.getPlayerName().equals(playerName)) {
            viewHolder.tvPlayerName.setText(item.getPlayerName() + "(我)");
        } else {
            viewHolder.tvPlayerName.setText(item.getPlayerName());
        }
        viewHolder.tvCreateTime.setText(item.getCreateTime().replace(" ", "\n"));
        setMedalImage(viewHolder.ivMedal, position);
    }

    private void updateNormalView(NormalViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.tvLevelName.setText(item.getLevelName());
        if (item.getPlayerName().equals(playerName)) {
            viewHolder.tvPlayerName.setTextColor(ResourceUtil.getColor(context, R.color.light_blue));
            viewHolder.tvPlayerName.setText(item.getPlayerName() + "(我)");
        } else {
            viewHolder.tvPlayerName.setTextColor(ResourceUtil.getColor(context, R.color.white));
            viewHolder.tvPlayerName.setText(item.getPlayerName());
        }
        viewHolder.tvCreateTime.setText(item.getCreateTime().replace(" ", "\n"));
        int number = position + 1;//Item从1排起
        setNumberImage(viewHolder.ivNumberOne, number / 10);
        setNumberImage(viewHolder.ivNumberTwo, number % 10);
    }

    private void setMedalImage(ImageView imageView, int position) {
        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_player_level_list_medal_first);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_player_level_list_medal_second);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_player_level_list_medal_third);
                break;
        }
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

    static class BestViewHolder {
        @Bind(R.id.iv_medal)
        ImageView ivMedal;
        @Bind(R.id.tv_level_name)
        TextView tvLevelName;
        @Bind(R.id.tv_player_name)
        TextView tvPlayerName;
        @Bind(R.id.tv_create_time)
        TextView tvCreateTime;

        BestViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

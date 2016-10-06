package com.wang.catchcrazycat;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.android_lib.util.WindowUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.btn_menu)
    ImageView btnMenu;
    @Bind(R.id.play_ground)
    Playground playground;

    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.transStateBar(this);
        setContentView(R.layout.activity_main);
        Util.context = getApplicationContext();
        ButterKnife.bind(this);
        initPopupMenu();
    }

    private void initPopupMenu() {
        popupMenu = new PopupMenu(this, btnMenu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_menu_play_new:
                        playground.playNew();
                        break;
                    case R.id.btn_menu_game_description:
                        M.t(MainActivity.this, "游戏说明");
                        break;
                    case R.id.btn_menu_choose_level:
                        M.t(MainActivity.this, "选择等级");
                        chooseLevel();
                        break;
                    case R.id.btn_menu_about:
                        M.t(MainActivity.this, "关于");
                        break;
                }
                return true;
            }
        });
    }

    private void chooseLevel() {
        DialogUtil.showInputDialog(this, "选择等级", "初始路障熟路", "10", new DialogUtil.OnInputFinishListener() {
            @Override
            public void onInputFinish(String text) {
                try {
                    playground.blockNumber = Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "请输入数字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btn_menu)
    public void onClick() {
        popupMenu.show();
    }
}

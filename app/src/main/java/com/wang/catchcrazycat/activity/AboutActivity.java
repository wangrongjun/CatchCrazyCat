package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wang.android_lib.util.IntentUtil;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.constant.C;
import com.wang.catchcrazycat.util.Util;
import com.wang.java_util.StreamUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * by 王荣俊 on 2016/10/8.
 */
public class AboutActivity extends Activity {

    @Bind(R.id.tv_game_introduction)
    TextView tvGameIntroduction;
    @Bind(R.id.tv_create_background)
    TextView tvCreateBackground;
    @Bind(R.id.tv_source_code_url)
    TextView tvSourceCodeUrl;
    @Bind(R.id.tv_app_version)
    TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        tvAppVersion.setText("v" + Util.getVersionName(this));

        try {
            String gameIntroduction = StreamUtil.readInputStream(getAssets().open("game_introduction.txt"));
            tvGameIntroduction.setText(gameIntroduction);

            String createBackground = StreamUtil.readInputStream(getAssets().open("create_background.txt"));
            tvCreateBackground.setText(createBackground);

            tvSourceCodeUrl.setText(C.sourseCodeUrl());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.btn_check_update, R.id.btn_my_website, R.id.tv_source_code_url})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_update:
                Util.startCheckUpdateAndShowToPlayer(this);
                break;
            case R.id.btn_my_website:
                startActivity(IntentUtil.getUrlIntent(C.hostHrl));
                break;
            case R.id.tv_source_code_url:
                startActivity(IntentUtil.getUrlIntent(C.sourseCodeUrl()));
                break;
        }
    }

}

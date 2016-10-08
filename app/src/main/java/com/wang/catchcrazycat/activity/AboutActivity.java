package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wang.android_lib.util.IntentUtil;
import com.wang.catchcrazycat.R;
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

    private String sourceCodeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        try {
            String gameIntroduction = StreamUtil.readInputStream(getAssets().open("game_introduction.txt"));
            tvGameIntroduction.setText(gameIntroduction);

            String createBackground = StreamUtil.readInputStream(getAssets().open("create_background.txt"));
            tvCreateBackground.setText(createBackground);

            sourceCodeUrl = StreamUtil.readInputStream(getAssets().open("source_code_url.txt"));
            tvSourceCodeUrl.setText(sourceCodeUrl);
//            tvSourceCodeUrl.setAutoLinkMask(Linkify.ALL);
//            tvSourceCodeUrl.setMovementMethod(LinkMovementMethod.getInstance());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.tv_source_code_url)
    public void onClick() {
        startActivity(IntentUtil.getUrlIntent(sourceCodeUrl));
    }

}

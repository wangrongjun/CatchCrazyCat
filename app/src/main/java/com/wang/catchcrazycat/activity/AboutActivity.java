package com.wang.catchcrazycat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wang.android_lib.helper.AndroidHttpHelper;
import com.wang.android_lib.util.AndroidHttpUtil;
import com.wang.android_lib.util.IntentUtil;
import com.wang.android_lib.util.M;
import com.wang.catchcrazycat.R;
import com.wang.catchcrazycat.constant.C;
import com.wang.catchcrazycat.util.Util;
import com.wang.java_program.app_upgrade.bean.AppLatestVersion;
import com.wang.java_util.HttpUtil;
import com.wang.java_util.StreamUtil;
import com.wang.json_result.JsonResult;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

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
                startCheckUpdate();
                break;
            case R.id.btn_my_website:
                startActivity(IntentUtil.getUrlIntent(C.hostHrl));
                break;
            case R.id.tv_source_code_url:
                startActivity(IntentUtil.getUrlIntent(C.sourseCodeUrl()));
                break;
        }
    }

    private void startCheckUpdate() {

        AndroidHttpHelper helper = new AndroidHttpHelper(this);
        helper.setDialogHint("正在检测更新").setOnSucceedListener(new AndroidHttpUtil.OnSucceedListener() {
            @Override
            public void onSucceed(HttpUtil.Result r) {

                JsonResult jsonResult = new Gson().fromJson(r.result, JsonResult.class);
                if (jsonResult.getState() == JsonResult.OK) {

                    AppLatestVersion latestVersion = new Gson().fromJson(jsonResult.getResult(),
                            AppLatestVersion.class);

                    if (latestVersion.getVersionCode() > Util.getVersionCode(AboutActivity.this)) {
                        showUpgradeDialog(latestVersion);
                    } else {
                        Toast.makeText(AboutActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    M.t(AboutActivity.this, "服务器出错：\n" + jsonResult.getResult());
                }

            }
        }).request(C.queryLatestVersionUrl());

    }

    private void showUpgradeDialog(final AppLatestVersion latestVersion) {

        String message = "当前版本：v" + Util.getVersionName(this) + "\n\n";
        message += "最新版本：v" + latestVersion.getVersionName() + "\n\n";
        message += "更新说明：\n" + latestVersion.getDescription();

        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.setTitle("软件升级").setMessage(message);
        dialog.setPositiveButton("立即下载升级", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(IntentUtil.getUrlIntent(latestVersion.getApkFileUrl()));
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}

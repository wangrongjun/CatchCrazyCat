package com.wang.catchcrazycat.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wang.android_lib.util.M;
import com.wang.catchcrazycat.activity.MainActivity;
import com.wang.catchcrazycat.game.Dot;
import com.wang.catchcrazycat.game.DotManager;
import com.wang.catchcrazycat.game.LevelRule;
import com.wang.catchcrazycat.util.BmobUtil;
import com.wang.catchcrazycat.util.P;
import com.wang.catchcrazycat.util.Util;
import com.wang.common_lib.MaterialDialogUtil;
import com.wang.java_util.TextUtil;

/**
 * by 王荣俊 on 2016/10/6.
 * http://blog.sina.com.cn/s/blog_706c449f01011hcr.html SurfaceView设置背景透明
 * mySurfaceView.setZOrderOnTop(true);//设置画布  背景透明
 * mySurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
 */
public class Playground extends SurfaceView implements View.OnTouchListener {

    private Context context;
    private int dotWidth;
    private int playgroundSize = 11;

    private int step = 0;//已经行走的步数
    private int currentLevel;

    private DotManager manager;

    public Playground(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getHolder().addCallback(callback);

        currentLevel = P.getPlayerMaxLevel() + 1;//默认情况下挑战最新的未挑战成功的等级
        if (currentLevel > LevelRule.getMaxLevel()) {
            currentLevel--;
        }
        sendCurrentLevelChangedBroadcast();
        sendMaxLevelChangedBroadcast();

        setZOrderOnTop(true);//设置画布  背景透明
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    public void playNew() {
        int blockNumber = LevelRule.getBlockNumber(currentLevel);
        step = 0;
        sendStepChangedBroadcast();
        manager = new DotManager(playgroundSize, playgroundSize, blockNumber);
        allowTouch(true);
        redraw();
    }

    private void allowTouch(boolean allowTouch) {
        if (allowTouch) {
            setOnTouchListener(this);
        } else {
            setOnTouchListener(null);
        }
    }

    private void redraw() {
        final Canvas canvas = getHolder().lockCanvas();
//        canvas.drawColor(Color.LTGRAY);

        final Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        manager.iterator(new DotManager.INext() {
            @Override
            public boolean next(float x, float y, int dotState) {

                RectF rectF = new RectF(
                        x * dotWidth,
                        y * dotWidth,
                        (x + 1) * dotWidth,
                        (y + 1) * dotWidth
                );

                switch (dotState) {
                    case Dot.State_Null:
                        paint.setColor(0xFF888888);
                        canvas.drawOval(rectF, paint);
                        break;
                    case Dot.State_Block:
                        paint.setColor(0xFFFFAA00);
                        canvas.drawOval(rectF, paint);
                        break;
                    case Dot.State_Cat:
                        paint.setColor(0xFFFF0000);
                        canvas.drawOval(rectF, paint);
                        break;
                }

                return true;
            }
        });

        getHolder().unlockCanvasAndPost(canvas);
    }

    private boolean isSurfaceCreated = false;

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!isSurfaceCreated) {
                playNew();
                isSurfaceCreated = true;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            dotWidth = width / (playgroundSize + 1);
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    @Override
    public boolean onTouch(View v, final MotionEvent event) {

        if (event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }

        manager.iterator(new DotManager.INext() {
            @Override
            public boolean next(float x, float y, int dotState) {//根据触摸的x,y坐标计算出对应的dot的x,y坐标
                if (y * dotWidth <= event.getY() && event.getY() <= (y + 1) * dotWidth) {
                    if (x * dotWidth <= event.getX() && event.getX() <= (x + 1) * dotWidth) {
                        if (dotState == Dot.State_Null) {
                            manager.setBlock((int) x, (int) y);
                            step++;
                            move();
                            sendStepChangedBroadcast();
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        return true;
    }

    private void move() {

        int gameState = manager.calculateNextAndMoveCat();
        redraw();

        //若步数已经大于当前等级允许的最大步数
        if (LevelRule.getMaxStep(currentLevel) < step) {
            allowTouch(false);
            Util.showLossDialog(context, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    playNew();
                }
            });
            return;
        }

        switch (gameState) {
            case DotManager.STATE_WIN:
                allowTouch(false);
                handleWin();
                break;
            case DotManager.STATE_LOSS:
                allowTouch(false);
                Util.showLossDialog(context, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playNew();
                    }
                });
                break;
            case DotManager.STATE_CONTINUE:
                break;
        }
    }

    private void handleWin() {

        if (P.getPlayerMaxLevel() < currentLevel) {
            P.setPlayerMaxLevel(currentLevel);
            sendMaxLevelChangedBroadcast();
        }

        //若大于等于可以登榜的最低等级，并且当前等级就是最高等级，可以记录到榜单上
        if (currentLevel >= LevelRule.getShowPlayerListMinLevel() && P.getPlayerMaxLevel() == currentLevel) {

            if (!TextUtil.isEmpty(P.getPlayerName())) {//若已经设置了玩家名，可以直接上传成绩
                BmobUtil.startDeleteOldIfExistsAndUploadLevel(context, P.getPlayerName(), currentLevel);
                showWinDialogAndQueryNext();

            } else {//否则先设置玩家名，再上传成绩同时询问是否进行下一关
                MaterialDialogUtil.showInput(context, "英雄！请留名！", "", new MaterialDialogUtil.OnInputFinishListener() {
                    @Override
                    public void onInputFinish(String input) {
                        showWinDialogAndQueryNext();
                        if (!TextUtil.isEmpty(input)) {
                            P.setPlayerName(input);
                            sendShowPlayerNameBroadcast();
                            BmobUtil.startDeleteOldIfExistsAndUploadLevel(context, P.getPlayerName(), currentLevel);
                        }
                    }
                });
            }

        } else {//否则直接询问是否进行下一关
            showWinDialogAndQueryNext();
        }
    }

    private void showWinDialogAndQueryNext() {
        Util.showWinDialog(context, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playNextLevel(true);
            }
        });
    }

    /**
     * 设置新的当前等级并以新的当前等级重玩
     */
    public void setCurrentLevelAndPlay(int currentLevel) {
        this.currentLevel = currentLevel;
        sendCurrentLevelChangedBroadcast();
        playNew();
    }

    public void playPreviousLevel() {
        if (currentLevel == LevelRule.getMinLevel()) {
            M.t(context, "当前已经是第一级");
            return;
        }
        currentLevel--;
        sendCurrentLevelChangedBroadcast();
        playNew();
    }

    public void playNextLevel(boolean allowHigherThanMaxLevel) {

        if (!allowHigherThanMaxLevel) {
            if (currentLevel > P.getPlayerMaxLevel()) {//若当前等级比玩家达到的最高等级还高一级，就不能下一级了。
                M.t(context, "请先挑战当前等级");
                return;
            }
        }

        if (currentLevel == LevelRule.getMaxLevel()) {
            M.t(context, "恭喜！您已经破了最高等级！");
            return;
        }
        currentLevel++;
        sendCurrentLevelChangedBroadcast();
        playNew();
    }

    private void sendCurrentLevelChangedBroadcast() {
        Intent intent = new Intent(MainActivity.ACTION_CURRENT_LEVEL_CHANGED);
        intent.putExtra("currentLevel", currentLevel);
        context.sendBroadcast(intent);
    }

    private void sendMaxLevelChangedBroadcast() {
        Intent intent = new Intent(MainActivity.ACTION_MAX_LEVEL_CHANGED);
        context.sendBroadcast(intent);
    }

    private void sendStepChangedBroadcast() {
        Intent intent = new Intent(MainActivity.ACTION_STEP_CHANGED);
        intent.putExtra("step", step);
        context.sendBroadcast(intent);
    }

    private void sendShowPlayerNameBroadcast() {
        Intent intent = new Intent(MainActivity.ACTION_SHOW_PLAYER_NAME);
        context.sendBroadcast(intent);
    }

}

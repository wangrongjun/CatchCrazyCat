package com.wang.catchcrazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.wang.android_lib.util.DialogUtil;

/**
 * by 王荣俊 on 2016/10/6.
 */
public class Playground extends SurfaceView implements View.OnTouchListener {

    private Context context;
    private int dotWidth;
    public int playgroundSize = 11;
    public int blockNumber = 0;

    private DotManager manager;

    public Playground(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getHolder().addCallback(callback);
    }

    public void playNew() {
        manager = new DotManager(playgroundSize, playgroundSize, blockNumber);
        allowTouch(true);
        redraw();
    }

    public void allowTouch(boolean allowTouch) {
        if (allowTouch) {
            setOnTouchListener(this);
        } else {
            setOnTouchListener(null);
        }
    }

    public void redraw() {
        final Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.LTGRAY);

        final Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        manager.iterator(new DotManager.INext() {
            @Override
            public boolean next(float x, float y, int dotState) {

                switch (dotState) {
                    case Dot.State_Null:
                        paint.setColor(0xFFEEEEEE);
                        break;
                    case Dot.State_Block:
                        paint.setColor(0xFFFFAA00);
                        break;
                    case Dot.State_Cat:
                        paint.setColor(0xFFFF0000);
                        break;
                }
                canvas.drawOval(new RectF(
                        x * dotWidth,
                        y * dotWidth,
                        (x + 1) * dotWidth,
                        (y + 1) * dotWidth
                ), paint);

                return true;
            }
        });

        getHolder().unlockCanvasAndPost(canvas);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            playNew();
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
                            move();
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
        switch (gameState) {
            case DotManager.STATE_WIN:
                allowTouch(false);
                DialogUtil.showConfirmDialog(context, "提示", "恭喜你！你赢了！", null);
                break;
            case DotManager.STATE_LOSS:
                allowTouch(false);
                DialogUtil.showConfirmDialog(context, "提示", "对不起，你输了。", null);
                break;
            case DotManager.STATE_CONTINUE:
                break;
        }
    }
}

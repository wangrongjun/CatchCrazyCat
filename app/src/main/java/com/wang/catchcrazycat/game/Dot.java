package com.wang.catchcrazycat.game;

/**
 * by 王荣俊 on 2016/10/6.
 */
public class Dot {

    public static final int State_Null = 1;//空白点
    public static final int State_Block = 2;//路障点
    public static final int State_Cat = 3;//猫点（神经猫所在点）

    private int x;
    private int y;
    private int state;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        state = State_Null;
    }

    public Dot(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

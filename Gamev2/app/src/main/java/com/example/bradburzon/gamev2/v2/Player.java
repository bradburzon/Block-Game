package com.example.bradburzon.gamev2.v2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Brad_Mike Burzon on 7/19/2017.
 */

public class Player implements DrawableObject {
    private long screenWidth;
    private long screenHeight;
    private int left, top, right, bottom, side;
    private Paint paint;

    public Player(long screenWidth, long screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        side = 100;
        left = ((int) screenWidth / 2) - 50;
        top = (int) screenHeight - side;
        right = ((int) screenWidth / 2) + 50;
        bottom = (int) screenHeight;
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);
    }

    public static Player makePlayer(long screenWidth, long screenHeight) {
        return new Player(screenWidth, screenHeight);
    }

    public long getScreenWidth() {
        return screenWidth;
    }

    public long getScreenHeight() {
        return screenHeight;
    }

    public void draw(Canvas canvas) {
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean hasCollided(DrawableObject object) {

        if (left < object.getRight() && right > object.getLeft()
                && top < object.getBottom() && bottom > object.getTop()) {
            return true;
        } else {
            return false;
        }
    }

    public void moveUp() {
        top -= side;
        bottom -= side;
    }

    public boolean isHalfWay(){
        if(getTop() < (screenHeight / 2))
            return true;
        else
            return false;
    }

    public void movePlayer(int velocity){
        top += velocity;
        bottom += velocity;
    }
    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

}

package com.example.brad_mikeburzon.game.v2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Brad_Mike Burzon on 7/20/2017.
 */

public class Obstacle implements DrawableObject {
    private long screenWidth;
    private long screenHeight;
    private int left, top, right, bottom, side, velocity;
    private Paint paint;
    private long lastUpdated, framePerSecond;

    public Obstacle(long screenWidth, long screenHeight, int top) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        side = 100;
        left = ((int) screenWidth / 2) - 50;
        this.top = top;
        right = ((int) screenWidth / 2) + 50;
        bottom = top + side;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        velocity = new Random().nextInt(50) + 20;
        lastUpdated = 0;
        framePerSecond = 1000 / 30;
    }

    public static List<Obstacle> makeObstacles(long screenWidth, long screenHeight, int count) {
        List<Obstacle> obstacles = new ArrayList<>();
        int topPos = (int) screenHeight - 300;
        for(int i = 0; i < count; i++){
            if((i % 2) == 0){
                obstacles.add(new Obstacle(screenWidth, screenHeight, topPos));
                topPos-=200;
            }
        }
        return obstacles;
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
        return false;
    }

    public void update(long currentTime){
        Log.d("MIKE", "Obstacle.update got called " + (currentTime >= lastUpdated + framePerSecond));
        if(currentTime >= lastUpdated + framePerSecond){
            lastUpdated = currentTime;
            if(left >= screenWidth - side || left < 0){
                velocity *= -1;
            }
            left += velocity;
            right += velocity;
            Log.d("MIKE", "Obstacle.update new left and right [" + left + ", " + right + "]");

        }
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

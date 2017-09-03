package com.example.bradburzon.gamev2.v2;

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
    private int left, top, right, bottom, side;
    private double velocity;
    private int maxSpeed, minSpeed;
    private Paint paint;
    private long lastUpdated, framePerSecond;
    private boolean moveDifficulty;

    public Obstacle(long screenWidth, long screenHeight, int top) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        side = 100;
        int random = new Random().nextInt(600)-300;
        left = ((int) screenWidth / 2) - 50 +random;
        this.top = top;
        right = ((int) screenWidth / 2) + 50 +random;
        bottom = top + side;
        paint = new Paint();
        paint.setColor(Color.RED);

        paint.setStyle(Paint.Style.FILL);
        maxSpeed = 5;
        minSpeed = 5;
        moveDifficulty = true;
        setSpeed(maxSpeed, minSpeed);
        lastUpdated = 0;
        framePerSecond = 1000 / 30;
    }

    public static List<Obstacle> makeObstacles(long screenWidth, long screenHeight, int count) {
        List<Obstacle> obstacles = new ArrayList<>();
        int topPos = (int) screenHeight - 300;
        for (int i = 0; i < count; i++) {
            if ((i % 2) == 0) {
                obstacles.add(new Obstacle(screenWidth, screenHeight, topPos));
                topPos -= 200;
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


    private void setSpeed(int maxSpeed, int minSpeed) {
        velocity = new Random().nextInt(maxSpeed) + minSpeed;
        if(velocity % 2 == 0)
            velocity *= -1;
    }


    public void moveObstacle(int velocity) {
        top += velocity;
        bottom += velocity;
    }

    public void startOver() {
        if (top >= screenHeight) {
            int random = new Random().nextInt(600)-300;
            left = ((int) screenWidth / 2) - 50 +random;
            top -= 12 * side;
            right = ((int) screenWidth / 2) + 50 +random;
            bottom -= 12 * side;
            increaseVelocity(1);
        }
    }

    private void increaseVelocity(int i) {
        maxSpeed += i;
        minSpeed += i;
        setSpeed(maxSpeed, minSpeed);
    }

    @Override
    public boolean hasCollided(DrawableObject object) {
        return false;
    }

    public void update(long currentTime) {
        // Log.d("MIKE", "Obstacle.update got called " + (currentTime >= lastUpdated + framePerSecond));
        if (currentTime >= lastUpdated + framePerSecond) {
            lastUpdated = currentTime;
            if (left >= screenWidth - side || left < 0) {
                velocity *= -1;
            }
            left += velocity;
            right += velocity;
            //  Log.d("MIKE", "Obstacle.update new left and right [" + left + ", " + right + "]");
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

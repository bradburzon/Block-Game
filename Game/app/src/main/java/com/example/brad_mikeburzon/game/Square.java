package com.example.brad_mikeburzon.game;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by Brad_Mike Burzon on 7/19/2017.
 */

public class Square {

    private int velocity;
    private final int side;
    private int xPos, yPos;
    private final int color;
    private final int framePerSecond;
    private final int screenWidth, screenHeight;
    private long lastUpdated;
    private Random rand;

    public Square(int width, int height, int xPos, int yPos, int framePerSecond, int color){
        rand = new Random();
        screenHeight = height;
        screenWidth = width;
        this.xPos = xPos;
        this.yPos = yPos;

        this.framePerSecond = 1000 / framePerSecond;
        this.color = color;
        velocity = 10;
        side = 100;
    }

    //constructor for obstacles
    public Square(int width, int height, int yPos, int fps, int side){
        rand = new Random();
        screenHeight = height;
        screenWidth = width;
        this.xPos = rand.nextInt(screenWidth-side);
        this.yPos = yPos;
        framePerSecond = 1000 / fps;
        color = Color.WHITE;
        velocity = 10;
        this.side = side;
    }

    public void draw(Canvas canvas){
        Rect rectangle =  new Rect(xPos, yPos, xPos + side, yPos + side);
        Log.d("Mike", "square.draw");
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectangle, paint);
    }


    public void update(long currentTime){
        Log.d("Mike", "square.update");
        if(currentTime >= lastUpdated + framePerSecond){
            lastUpdated = currentTime;
            if(xPos >= screenWidth - side || xPos < 0){
                velocity *= -1;
            }
            xPos += velocity;
        }
    }

    public Square[] makeObstacles(int i) {
        Square[] obstacles = new Square[i];
        int yPosition = 0;
        int speed;

        if(i > 0) {
            for(int j = 0; j < i; j++){
                speed = rand.nextInt(70) + 30;
                obstacles[j] = new Square(screenWidth, screenHeight, yPosition, speed, side);
                yPosition += 100;
            }
        }
        return obstacles;
    }

    public void moveUpPlayer(){
        yPos -= side;
    }

    public boolean checkCollision(Square square){
        if(this.xPos >= square.xPos && xPos+side <= square.xPos) {
            return true;
        }
        return false;
    }

}

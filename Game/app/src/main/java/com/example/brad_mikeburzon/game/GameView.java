package com.example.brad_mikeburzon.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Brad_Mike Burzon on 7/18/2017.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private AnimationThread animationThread;
    private Square player;
    private Square[] obstacles;


    public GameView(Context context, int width, int height) {
        super(context);
        animationThread = new AnimationThread(this, getHolder());
        getHolder().addCallback(this);

        player = new Square(width, height, width / 2, height - 100, 1, Color.GREEN);
        obstacles = player.makeObstacles(10);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        animationThread.setIsGameOver(true);
        animationThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        animationThread.setIsGameOver(false);
        try {
            animationThread.join();
        } catch (Exception e){
            surfaceDestroyed(getHolder());
        }
    }

    public void onDraw(Canvas canvas){
        if(canvas != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            Log.d("Mike", "GameView.onDraw");
            player.draw(canvas);
            drawObstacles(canvas);
        }
    }

    private void drawObstacles(Canvas canvas) {
        for(Square obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }

    public void onUpdate(long time){
        Log.d("Mike", "GameView.update");
        player.update(time);
       updateObstacle(time);
    }

    public void updateObstacle(long time){
        for(Square obstacle : obstacles) {
            Log.d("collision", player.checkCollision(obstacle)+"");
            if(!player.checkCollision(obstacle)){
                obstacle.update(time);
            }
        }
    }

    public Square getPlayer(){
        return player;
    }
}

package com.example.brad_mikeburzon.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Brad_Mike Burzon on 7/19/2017.
 */
public class AnimationThread extends Thread{
    private boolean isGameover;
    private final GameView gameView;
    private final SurfaceHolder surfaceHolder;
    private long timer;

    public AnimationThread(GameView gameView, SurfaceHolder surfaceHolder){
        isGameover = false;
        timer = 0;
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        Canvas canvas;

        while(!isGameover){
            canvas = null;
            timer = System.currentTimeMillis();
            gameView.onUpdate(timer);
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    gameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setIsGameOver(boolean gameStatus){
        isGameover = gameStatus;
    }
}

package com.example.bradburzon.gamev2.v2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.bradburzon.gamev2.MainActivity;


/**
 * Created by Brad_Mike Burzon on 7/19/2017.
 */

public class AnimationThread extends Thread {
    private boolean isGameover;
    private final GameView gameView;
    private final SurfaceHolder surfaceHolder;
    private MainActivity activity;
    private long timer;
    private Canvas canvas;

    public AnimationThread(GameView gameView, SurfaceHolder surfaceHolder, MainActivity activity) {
        isGameover = false;
        timer = 0;
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
        this.activity = activity;
    }


    @Override
    public void run() {

        while (!isGameover) {
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
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showGameOver();
            }
        });
    }

    public void setIsGameOver(boolean gameStatus) {
        isGameover = gameStatus;
    }
}

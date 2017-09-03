package com.example.brad_mikeburzon.game.v2;

/**
 * Created by Brad_Mike Burzon on 7/20/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.brad_mikeburzon.game.AnimationThread;
import com.example.brad_mikeburzon.game.Square;

import java.util.List;

/**
 * Created by Brad_Mike Burzon on 7/18/2017.
 */
public class GameView extends com.example.brad_mikeburzon.game.GameView implements SurfaceHolder.Callback {
    private AnimationThread animationThread;
    private Player player;
    private List<Obstacle> obstacles;

    public GameView(Context context, int width, int height) {
        super(context, width, height);
        animationThread = new AnimationThread(this, getHolder());
        getHolder().addCallback(this);

        player = Player.makePlayer(width, height);
        obstacles = Obstacle.makeObstacles(width, height, 10);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        animationThread.setIsGameOver(false);
        animationThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //nothingggggg
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        animationThread.setIsGameOver(true);
        try {
            animationThread.join();
        } catch (Exception e) {
            surfaceDestroyed(getHolder());
        }
    }

    public void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            player.draw(canvas);
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(canvas);
            }

        }
    }

    public void onUpdate(long time) {
        Log.d("Mike", "GameView.update");
        updateObstacle(time);
    }

    public void updateObstacle(long time) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update(time);
            if (player.hasCollided(obstacle)) {
                animationThread.setIsGameOver(true);
            };
        }

    }

    public void movePlayer() {
        player.moveUp();
    }
}

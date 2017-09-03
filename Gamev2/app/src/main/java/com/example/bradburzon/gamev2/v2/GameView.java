package com.example.bradburzon.gamev2.v2;

/**
 * Created by Brad_Mike Burzon on 7/20/2017.
 */

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.bradburzon.gamev2.MainActivity;

import java.util.List;

/**
 * Created by Brad_Mike Burzon on 7/18/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private AnimationThread animationThread;
    private Player player;
    private int score;
    private List<Obstacle> obstacles;
    private Button resetButton;
    private int width, height;
    private MainActivity context;
   
    public GameView(MainActivity context, int width, int height) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        score = 0;
        animationThread = new AnimationThread(this, getHolder(), context);
        getHolder().addCallback(this);
        resetButton = new Button(context);
        resetButton.setWidth(300);
        resetButton.setText("Restart?");
        player = Player.makePlayer(width, height);
        obstacles = Obstacle.makeObstacles(width, height, 12);
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
    //    Log.d("Mike", "GameView.update");
        updateObstacle(time);
        if (player.isHalfWay()) {
            player.movePlayer(10);
            for (Obstacle obstacle : obstacles) {
                obstacle.moveObstacle(10);
                obstacle.startOver();

            }
        }
    }

    public void updateObstacle(long time) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update(time);
            if (player.hasCollided(obstacle)) {
                animationThread.setIsGameOver(true);
            }
        }
    }

    public void reset() {
        context.showGameOver();
    }

    public void movePlayer() {
        player.moveUp();
    }

    public void addOneToScore() {
        score++;
    }

    public int getScore() {
        return score;
    }
}

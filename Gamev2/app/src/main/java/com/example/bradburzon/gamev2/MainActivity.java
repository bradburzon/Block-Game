package com.example.bradburzon.gamev2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bradburzon.gamev2.v2.GameView;

public class MainActivity extends Activity {
    private static final String DEBUG_TAG = "abaji";
    private GameView gameView;
    private FrameLayout frameLayout;
    private LinearLayout gameOverMenu;
    private Button resetButton;
    private int width;
    private int height;
    private TextView scoreView;
    private boolean gameOver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar/title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameOver = false;

        //init gv
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x;
        height = size.y;
        gameView = new GameView(this, width, height);

        setupLayout();
        setContentView(frameLayout);
    }

    private void setupLayout() {
        frameLayout = new FrameLayout(this);
        gameOverMenu = new LinearLayout(this);
        resetButton = new Button(this);
        scoreView = new TextView(this);


        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.gravity = Gravity.BOTTOM;
        gameOverMenu.setGravity(Gravity.BOTTOM);
        gameOverMenu.setLayoutParams(ll);
        gameOverMenu.setOrientation(LinearLayout.VERTICAL);
        gameOverMenu.addView(scoreView);

        resetButton.setWidth(300);
        resetButton.setText("Reset game?");
        LinearLayout.LayoutParams rr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rr.setMargins(0, 50, 0, 0);
        resetButton.setLayoutParams(rr);
        scoreView.setLayoutParams(rr);

        scoreView.setText(gameView.getScore()+"");
        scoreView.setWidth(300);
        scoreView.setTextColor(Color.BLACK);
        scoreView.setTextSize(30);
        scoreView.setBackgroundColor(Color.WHITE);
        scoreView.setGravity(Gravity.CENTER);

        frameLayout.addView(gameView);
        frameLayout.addView(gameOverMenu);


//        hideGameOver();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView = new GameView(MainActivity.this, width, height);
                frameLayout.removeAllViews();
                frameLayout.addView(gameView);
                frameLayout.addView(gameOverMenu);
                hideGameOver();
            }
        });
    }

    public void showGameOver(){
        gameOver = true;
        gameOverMenu.addView(resetButton);
        int score = gameView.getScore();
        scoreView.setText("Score: " + score);
        gameOverMenu.setGravity(Gravity.CENTER);
        frameLayout.bringChildToFront(gameOverMenu);

    }

    public void hideGameOver(){
        gameOver = false;
        gameOverMenu.removeAllViews();
        gameOverMenu.addView(scoreView);
        gameOverMenu.setGravity(Gravity.BOTTOM);
        frameLayout.removeAllViews();
        frameLayout.addView(gameView);
        frameLayout.addView(gameOverMenu);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                gameView.movePlayer();
                gameView.addOneToScore();
                int score = gameView.getScore();

                if(!gameOver)
                scoreView.setText(score + "");
                return true;
            default:
                return false;
        }
    }
}

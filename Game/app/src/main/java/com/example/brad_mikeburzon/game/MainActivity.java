package com.example.brad_mikeburzon.game;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.example.brad_mikeburzon.game.v2.GameView;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "abaji";
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar/title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hides menu bar
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        gameView = new GameView(this, width, height);
        setContentView(gameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_UP :
                Log.d(DEBUG_TAG,"Action was UP");
                gameView.movePlayer();
                return true;
            default :
                return false;
        }
    }


}

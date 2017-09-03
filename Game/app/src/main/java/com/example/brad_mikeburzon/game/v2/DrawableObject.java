package com.example.brad_mikeburzon.game.v2;

import android.graphics.Canvas;

/**
 * Created by Brad_Mike Burzon on 7/20/2017.
 */

interface DrawableObject {
    public void draw(Canvas canvas);
    public boolean hasCollided(DrawableObject object);
    public int getTop();
    public int getLeft();
    public int getRight();
    public int getBottom();

}

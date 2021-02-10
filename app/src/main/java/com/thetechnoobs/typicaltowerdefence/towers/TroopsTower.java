package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;

import java.util.Random;

public class TroopsTower {
    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    boolean shooting = false;
    int arrowSpriteWidth;
    int arrowSpriteHeight;
    int arrowSpriteFrameLocX = 1;
    int arrowSpriteFrameLocY = 2;

    public TroopsTower(Context context, RectF location) {
        resources = context.getResources();
        this.location = location;

        setupBitmap();
    }

    public void draw(Canvas canvas){
        int x = arrowSpriteWidth * arrowSpriteFrameLocX - arrowSpriteWidth;
        int y = arrowSpriteHeight * arrowSpriteFrameLocY - arrowSpriteHeight;

        frame = new Rect(x, y, x + arrowSpriteWidth, y + arrowSpriteHeight);


        canvas.drawBitmap(towerBitmap, frame, location, null);
    }

    public void update(){

    }

    public void shootAnimation(){
        arrowSpriteFrameLocX++;

        if(arrowSpriteFrameLocX == 8){
            arrowSpriteFrameLocX = 1;
        }
    }

    private void setupBitmap(){
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        arrowSpriteWidth = towerBitmap.getWidth()/8;
        arrowSpriteHeight = towerBitmap.getHeight()/4;
    }

    public void setShowRange(boolean shouldShow) {

    }
}

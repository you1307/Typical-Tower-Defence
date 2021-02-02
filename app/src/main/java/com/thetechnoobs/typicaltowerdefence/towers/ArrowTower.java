package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;

public class ArrowTower {
    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    boolean shooting = false;
    int arrowSpriteWidth;
    int arrowSpriteHeight;
    int arrowSpriteFrameLocX = 1;
    int arrowSpriteFrameLocY = 3;
    ArrowTowerData arrowTowerData;

    public ArrowTower(Context context, RectF location, ArrowTowerData arrowTowerData) {
        resources = context.getResources();
        this.location = location;
        this.arrowTowerData = arrowTowerData;


        setupBitmap();
        settupRangeHitbox();
    }

    public void draw(Canvas canvas){
        int x = arrowSpriteWidth * arrowSpriteFrameLocX - arrowSpriteWidth;
        int y = arrowSpriteHeight * arrowSpriteFrameLocY - arrowSpriteHeight;

        frame = new Rect(x, y, x + arrowSpriteWidth, y + arrowSpriteHeight);


        canvas.drawBitmap(towerBitmap, frame, location, null);
        //canvas.drawRect();
    }

    public void update(){

    }

    public void shootAnimation(){
        arrowSpriteFrameLocX++;

        if(arrowSpriteFrameLocX == 8){
            arrowSpriteFrameLocX = 1;
        }
    }

    RectF rangeHitbox;
    private void settupRangeHitbox(){
        Rect temprec = new Rect(400, 400, 450, 450);
        rangeHitbox = new RectF();
        rangeHitbox.round(temprec);
    }

    private void setupBitmap(){
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        arrowSpriteWidth = towerBitmap.getWidth()/8;
        arrowSpriteHeight = towerBitmap.getHeight()/4;
    }
}

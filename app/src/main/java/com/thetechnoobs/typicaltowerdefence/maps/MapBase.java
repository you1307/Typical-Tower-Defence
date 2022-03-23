package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

//Plot size should be 80 by 80 dp

public abstract class MapBase {
    public Bitmap mapBitmap;
    Resources resources;
    int[] screenSize;
    Context context;
    Paint testPaint = new Paint();
    ArrayList<PlotHandler> plotHandlers;

    public void draw(Canvas canvas){
    }

    public abstract ArrayList<RectF> getEnemyPathPoints();

    public Bitmap getMapBitmap() {
        return mapBitmap;
    }

    public ArrayList<RectF> enemyPathPoints() {
        return null;
    }
}

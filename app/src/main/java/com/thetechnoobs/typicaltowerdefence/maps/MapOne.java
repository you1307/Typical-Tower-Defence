package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class MapOne {
    Bitmap mapOneBitmap;
    Resources resources;
    int[] screenSize;
    Context context;
    Paint towerBoxLocPaint = new Paint();
    Paint towerBoxLocPaintTaken = new Paint();
    ArrayList<PlotHandler> plotHandlers;

    public MapOne(int[] screenSize, Resources resources, Context context){
        this.screenSize = screenSize;
        this.resources = resources;
        this.context = context;
        towerBoxLocPaintTaken.setColor(Color.RED);
        towerBoxLocPaintTaken.setAlpha(70);
        plotHandlers = TowerLocations.getMapOnePlots(context);

        settupBitmap();
    }

    private void settupBitmap() {
        mapOneBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_one_no_plots);
        mapOneBitmap = Bitmap.createScaledBitmap(mapOneBitmap, screenSize[0], screenSize[1], false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mapOneBitmap, 0, 0, null);
    }

    public ArrayList<PlotHandler> getMapOnePlots(){
        return plotHandlers;
    }
}

package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class MapThree extends MapBase{

    public MapThree(int[] screenSize, Resources resources, Context context){
        this.screenSize = screenSize;
        this.resources = resources;
        this.context = context;
        testPaint.setColor(Color.RED);

        settupBitmap();
        plotHandlers = getMapOnePlots(context);
    }

    public ArrayList<PlotHandler> getMapOnePlots(Context context){
        ArrayList<PlotHandler> plotHandlers = new ArrayList<>();
        float plotWidth = mapBitmap.getWidth() / 5f;
        float plotHeight = mapBitmap.getHeight() / 10f;

        RectF plot1Loc = new RectF(
                mapBitmap.getWidth() / 4.5f,
                mapBitmap.getHeight() / 7.3f,
                mapBitmap.getWidth() / 4.5f + plotWidth,
                mapBitmap.getHeight() / 7.3f + plotHeight);

        RectF plot2Loc = new RectF(
                mapBitmap.getWidth() / 8f,
                mapBitmap.getHeight() / 1.8f,
                mapBitmap.getWidth() / 8f + plotWidth,
                mapBitmap.getHeight() / 1.8f + plotHeight);

        RectF plot3Loc = new RectF(
                mapBitmap.getWidth() / 2.08f,
                mapBitmap.getHeight() / 2.1f,
                mapBitmap.getWidth() / 2.08f + plotWidth,
                mapBitmap.getHeight() / 2.1f + plotHeight);

        RectF plot4Loc = new RectF(
                mapBitmap.getWidth() / 2.08f,
                mapBitmap.getHeight() / 1.6f,
                mapBitmap.getWidth() / 2.08f + plotWidth,
                mapBitmap.getHeight() / 1.6f + plotHeight);

        RectF plot5Loc = new RectF(
                mapBitmap.getWidth() / 1.6f,
                mapBitmap.getHeight() / 1.15f,
                mapBitmap.getWidth() / 1.6f + plotWidth,
                mapBitmap.getHeight() / 1.15f + plotHeight);

        PlotHandler plotHandler1 = new PlotHandler(plot1Loc, context, 1);
        PlotHandler plotHandler2 = new PlotHandler(plot2Loc, context, 2);
        PlotHandler plotHandler3 = new PlotHandler(plot3Loc, context, 3);
        PlotHandler plotHandler4 = new PlotHandler(plot4Loc, context, 4);
        PlotHandler plotHandler5 = new PlotHandler(plot5Loc, context, 5);

        plotHandlers.add(plotHandler1);
        plotHandlers.add(plotHandler2);
        plotHandlers.add(plotHandler3);
        plotHandlers.add(plotHandler4);
        plotHandlers.add(plotHandler5);

        return plotHandlers;
    }


    @Override
    public ArrayList<RectF> enemyPathPoints(){
        ArrayList<RectF> pathMarkers = new ArrayList<>();
        float pointWidth = mapBitmap.getWidth() / 120f;
        float pointHeight = mapBitmap.getHeight() / 220f;

        float mapWidth = mapBitmap.getWidth();
        float mapHeight = mapBitmap.getHeight();

        RectF point1 = new RectF(
                mapWidth / 1.2f,
                mapHeight / 1.23f,
                mapWidth / 1.2f + pointWidth,
                mapHeight / 1.23f + pointHeight);

        RectF point2 = new RectF(
                mapWidth / 2.3f,
                mapHeight / 1.28f,
                mapWidth / 2.3f + pointWidth,
                mapHeight / 1.28f + pointHeight);

        RectF point3 = new RectF(
                mapWidth / 2.8f,
                mapHeight / 1.5f,
                mapWidth / 2.8f + pointWidth,
                mapHeight / 1.5f + pointHeight);

        RectF point4 = new RectF(
                mapWidth / 2.2f,
                mapHeight / 2.3f,
                mapWidth / 2.2f + pointWidth,
                mapHeight / 2.3f + pointHeight);

        RectF point5 = new RectF(
                mapWidth / 1.2f,
                mapHeight / 2.5f,
                mapWidth / 1.2f + pointWidth,
                mapHeight / 2.5f + pointHeight);

        RectF point6 = new RectF(
                mapWidth / 1.15f,
                mapHeight / 3f,
                mapWidth / 1.15f + pointWidth,
                mapHeight / 3f + pointHeight);

        RectF point7 = new RectF(
                mapWidth / 1.3f,
                mapHeight / 3.6f,
                mapWidth / 1.3f + pointWidth,
                mapHeight / 3.6f + pointHeight);

        RectF point8 = new RectF(
                mapWidth / 3f,
                mapHeight / 4f,
                mapWidth / 3f + pointWidth,
                mapHeight / 4f + pointHeight);

        RectF point9 = new RectF(
                Tools.convertDpToPixel(-50),
                mapHeight / 3.5f,
                Tools.convertDpToPixel(-55),
                mapHeight / 3.5f + pointHeight);


        pathMarkers.add(point1);
        pathMarkers.add(point2);
        pathMarkers.add(point3);
        pathMarkers.add(point4);
        pathMarkers.add(point5);
        pathMarkers.add(point6);
        pathMarkers.add(point7);
        pathMarkers.add(point8);
        pathMarkers.add(point9);

        return pathMarkers;
    }

    private void settupBitmap() {
        mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_three_no_plots);
        mapBitmap = Bitmap.createScaledBitmap(mapBitmap, screenSize[0], screenSize[1], false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mapBitmap, 0, 0, null);
    }


    public ArrayList<PlotHandler> getMapThreePlots(){
        return plotHandlers;
    }
}

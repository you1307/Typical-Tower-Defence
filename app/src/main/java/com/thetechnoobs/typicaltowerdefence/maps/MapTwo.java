package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class MapTwo extends MapBase {
    ArrayList<RectF> enemyPathPoints;

    public MapTwo(int[] screenSize, Resources resources, Context context) {
        super();
        this.screenSize = screenSize;
        this.resources = resources;
        this.context = context;
        testPaint.setColor(Color.RED);
        settupBitmap();

        enemyPathPoints = enemyPathPoints();
        plotHandlers = getMapTwoPlots(context);
    }

    public ArrayList<PlotHandler> getMapTwoPlots(Context context) {
        ArrayList<PlotHandler> plotHandlers = new ArrayList<>();
        float plotWidth = mapBitmap.getWidth() / 5f;
        float plotHeight = mapBitmap.getHeight() / 10f;

        RectF plot1Loc = new RectF(
                mapBitmap.getWidth() / 4.5f,
                mapBitmap.getHeight() / 5f,
                mapBitmap.getWidth() / 4.5f + plotWidth,
                mapBitmap.getHeight() / 5f + plotHeight);

        RectF plot2Loc = new RectF(
                mapBitmap.getWidth() / 4.5f,
                mapBitmap.getHeight() / 2.3f,
                mapBitmap.getWidth() / 4.5f + plotWidth,
                mapBitmap.getHeight() / 2.3f + plotHeight);

        RectF plot3Loc = new RectF(
                mapBitmap.getWidth() / 1.54f,
                mapBitmap.getHeight() / 2.3f,
                mapBitmap.getWidth() / 1.54f + plotWidth,
                mapBitmap.getHeight() / 2.3f + plotHeight);

        RectF plot4Loc = new RectF(
                mapBitmap.getWidth() / 1.54f,
                mapBitmap.getHeight() / 1.5f,
                mapBitmap.getWidth() / 1.54f + plotWidth,
                mapBitmap.getHeight() / 1.5f + plotHeight);

        RectF plot5Loc = new RectF(
                mapBitmap.getWidth() / 1.6f,
                mapBitmap.getHeight() / 4.5f,
                mapBitmap.getWidth() / 1.6f + plotWidth,
                mapBitmap.getHeight() / 4.5f + plotHeight);

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
    public ArrayList<RectF> enemyPathPoints() {
        ArrayList<RectF> pathMarkers = new ArrayList<>();
        float pointWidth = mapBitmap.getWidth() / 120f;
        float pointHeight = mapBitmap.getHeight() / 220f;

        RectF point1 = new RectF(
                mapBitmap.getWidth() / 8f,
                mapBitmap.getHeight() / 1.1f,
                mapBitmap.getWidth() / 8f + pointWidth,
                mapBitmap.getHeight() / 1.1f + pointHeight);

        RectF point2 = new RectF(
                mapBitmap.getWidth() / 8.5f,
                mapBitmap.getHeight() / 1.25f,
                mapBitmap.getWidth() / 8.5f + pointWidth,
                mapBitmap.getHeight() / 1.25f + pointHeight);

        RectF point3 = new RectF(
                mapBitmap.getWidth() / 2f,
                mapBitmap.getHeight() / 1.23f,
                mapBitmap.getWidth() / 2f + pointWidth,
                mapBitmap.getHeight() / 1.23f + pointHeight);

        RectF point4 = new RectF(
                mapBitmap.getWidth() / 1.9f,
                mapBitmap.getHeight() / 2f,
                mapBitmap.getWidth() / 1.9f + pointWidth,
                mapBitmap.getHeight() / 2f + pointHeight);

        RectF point5 = new RectF(
                mapBitmap.getWidth() / 2.0f,
                mapBitmap.getHeight() / 7f,
                mapBitmap.getWidth() / 2.0f + pointWidth,
                mapBitmap.getHeight() / 7f + pointHeight);

        RectF point6 = new RectF(
                Tools.convertDpToPixel(-50),
                mapBitmap.getHeight() / 7f,
                Tools.convertDpToPixel(-55),
                mapBitmap.getHeight() / 7f + pointHeight);


        pathMarkers.add(point1);
        pathMarkers.add(point2);
        pathMarkers.add(point3);
        pathMarkers.add(point4);
        pathMarkers.add(point5);
        pathMarkers.add(point6);

        return pathMarkers;
    }

    private void settupBitmap() {
        mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_two_no_plots);
        mapBitmap = Bitmap.createScaledBitmap(mapBitmap, screenSize[0], screenSize[1], false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mapBitmap, 0, 0, null);
    }

    @Override
    public ArrayList<RectF> getEnemyPathPoints() {
        return enemyPathPoints;
    }

    public ArrayList<PlotHandler> getMapTwoPlots() {
        return plotHandlers;
    }
}

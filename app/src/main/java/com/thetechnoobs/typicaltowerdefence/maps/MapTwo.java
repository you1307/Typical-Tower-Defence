package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class MapTwo extends MapBase{
    Bitmap mapTwoBitmap;
    Resources resources;
    int[] screenSize;
    Context context;
    Paint testPaint = new Paint();
    ArrayList<PlotHandler> plotHandlers;

    public MapTwo(int[] screenSize, Resources resources, Context context){
        this.screenSize = screenSize;
        this.resources = resources;
        this.context = context;
        testPaint.setColor(Color.RED);
        plotHandlers = getMapTwoPlots(context);

        settupBitmap();
    }

    public  static ArrayList<PlotHandler> getMapTwoPlots(Context context){
        ArrayList<PlotHandler> plotHandlers = new ArrayList<>();

        RectF plot1Loc = new RectF(
                Tools.convertDpToPixel(75),
                Tools.convertDpToPixel(150),
                Tools.convertDpToPixel(155),
                Tools.convertDpToPixel(228));

        RectF plot2Loc = new RectF(
                Tools.convertDpToPixel(75),
                Tools.convertDpToPixel(335),
                Tools.convertDpToPixel(155),
                Tools.convertDpToPixel(420));

        RectF plot3Loc = new RectF(
                Tools.convertDpToPixel(230),
                Tools.convertDpToPixel(305),
                Tools.convertDpToPixel(313),
                Tools.convertDpToPixel(390));

        RectF plot4Loc = new RectF(
                Tools.convertDpToPixel(230),
                Tools.convertDpToPixel(475),
                Tools.convertDpToPixel(313),
                Tools.convertDpToPixel(555));

        RectF plot5Loc = new RectF(
                Tools.convertDpToPixel(220),
                Tools.convertDpToPixel(130),
                Tools.convertDpToPixel(300),
                Tools.convertDpToPixel(215));

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

        RectF point1 = new RectF(
                Tools.convertDpToPixel(45),
                Tools.convertDpToPixel(700),
                Tools.convertDpToPixel(50),
                Tools.convertDpToPixel(705));

        RectF point2 = new RectF(
                Tools.convertDpToPixel(50),
                Tools.convertDpToPixel(600),
                Tools.convertDpToPixel(55),
                Tools.convertDpToPixel(605));

        RectF point3 = new RectF(
                Tools.convertDpToPixel(190),
                Tools.convertDpToPixel(540),
                Tools.convertDpToPixel(195),
                Tools.convertDpToPixel(545));

        RectF point4 = new RectF(
                Tools.convertDpToPixel(190),
                Tools.convertDpToPixel(280),
                Tools.convertDpToPixel(195),
                Tools.convertDpToPixel(285));

        RectF point5 = new RectF(
                Tools.convertDpToPixel(185),
                Tools.convertDpToPixel(120),
                Tools.convertDpToPixel(190),
                Tools.convertDpToPixel(125));

        RectF point6 = new RectF(
                Tools.convertDpToPixel(-50),
                Tools.convertDpToPixel(100),
                Tools.convertDpToPixel(-55),
                Tools.convertDpToPixel(105));


        pathMarkers.add(point1);
        pathMarkers.add(point2);
        pathMarkers.add(point3);
        pathMarkers.add(point4);
        pathMarkers.add(point5);
        pathMarkers.add(point6);

        return pathMarkers;
    }

    private void settupBitmap() {
        mapTwoBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_two_no_plots);
        mapTwoBitmap = Bitmap.createScaledBitmap(mapTwoBitmap, screenSize[0], screenSize[1], false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mapTwoBitmap, 0, 0, null);
    }

    public ArrayList<PlotHandler> getMapTwoPlots(){
        return plotHandlers;
    }
}

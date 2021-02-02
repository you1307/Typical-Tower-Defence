package com.thetechnoobs.typicaltowerdefence.maps;

import android.content.Context;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class TowerLocations {
    public static ArrayList<PlotHandler> getMapOnePlots(Context context){
        ArrayList<PlotHandler> plotHandlers = new ArrayList<>();

        RectF plot1Loc = new RectF(
                Tools.convertDpToPixel(50),
                Tools.convertDpToPixel(92),
                Tools.convertDpToPixel(130),
                Tools.convertDpToPixel(170));

        RectF plot2Loc = new RectF(
                Tools.convertDpToPixel(55),
                Tools.convertDpToPixel(335),
                Tools.convertDpToPixel(136),
                Tools.convertDpToPixel(420));

        RectF plot3Loc = new RectF(
                Tools.convertDpToPixel(220),
                Tools.convertDpToPixel(305),
                Tools.convertDpToPixel(303),
                Tools.convertDpToPixel(390));

        RectF plot4Loc = new RectF(
                Tools.convertDpToPixel(220),
                Tools.convertDpToPixel(497),
                Tools.convertDpToPixel(300),
                Tools.convertDpToPixel(582));

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
}

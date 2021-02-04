package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.thetechnoobs.typicaltowerdefence.enemys.TestRect;
import com.thetechnoobs.typicaltowerdefence.maps.MapOne;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;
import com.thetechnoobs.typicaltowerdefence.ui.CoinHeader;
import com.thetechnoobs.typicaltowerdefence.ui.InfoBuyPage;
import com.thetechnoobs.typicaltowerdefence.ui.InfoUpgradePage;
import com.thetechnoobs.typicaltowerdefence.ui.TowerBuySelectWheel;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    boolean running = true;
    private int frames;
    private double delta;
    ArrayList<PlotHandler> plotHandlers = new ArrayList<>();
    int plotInFocus;
    int[] screenSize;
    Canvas canvas;
    CoinHeader coinHeader;
    TowerBuySelectWheel towerSelectionWheel;
    InfoBuyPage infoBuyPage;
    InfoUpgradePage infoUpgradePage;
    ArrayList<TestRect> targets = new ArrayList<>();

    public GameView(Context context, int[] screenSize) {
        super(context);
        this.screenSize = screenSize;
        coinHeader = new CoinHeader(getResources(), context);
        setMapData();

        infoBuyPage = new InfoBuyPage(context);
        infoUpgradePage = new InfoUpgradePage(context);
        towerSelectionWheel = new TowerBuySelectWheel(getResources(), infoBuyPage);

    }

    @Override
    public void run() {
        while (running) {
            long lastTime = System.nanoTime();
            final double amountOfTicks = 60.0;
            double ns = 1000000000 / amountOfTicks;
            int ticks = 0;
            frames = 0;
            long timer = System.currentTimeMillis();

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (delta >= 1) {
                    tick();
                    ticks++;
                    delta--;
                }

                updateGraphics();
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    Log.v("fps", "FPS : " + frames + " Ticks : " + ticks);
                    frames = 0;
                    ticks = 0;
                }
            }
        }
    }


    private void tick() {
        if(targets.size() < 3){
            addTestEnemy();
        }

        for(int t = 0; t < targets.size(); t++){
            if(targets.get(t).shouldRemove()){
                targets.remove(t);
            }else{
                targets.get(t).update();
            }
        }

        for (PlotHandler plotHandler: plotHandlers){
            plotHandler.update(targets);
        }
    }

    private void updateGraphics() {
        if (getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            mapOne.draw(canvas);
            coinHeader.draw(canvas);


            for(TestRect t: targets){
                t.draw(canvas);
            }

            for (PlotHandler plotHandler : plotHandlers) {
                plotHandler.draw(canvas);
            }

            if (towerSelectionWheel.shouldShow()) {
                towerSelectionWheel.draw(canvas);
            }

            if (infoBuyPage.shouldShow()) {
                infoBuyPage.draw(canvas);
            }

            if(infoUpgradePage.shouldShow()){
                infoUpgradePage.draw(canvas);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RectF touchPoint = new RectF(
                event.getX() - Tools.convertDpToPixel(2),
                event.getY() - Tools.convertDpToPixel(2),
                event.getX() + Tools.convertDpToPixel(5),
                event.getY() + Tools.convertDpToPixel(5));

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presedDown(touchPoint);
                break;
        }

        return true;
    }

    private void presedDown(RectF touchPoint) {
        boolean plotTouched = false;

        if (infoBuyPage.shouldShow() && infoBuyPage.buyButtonPushed(touchPoint)) {
            plotInPos(plotInFocus).setTowerType(infoBuyPage.getLoadedTower());
            infoBuyPage.setShowMe(false);
            towerSelectionWheel.setShowMe(false);
            return;
        }

        if (towerSelectionWheel.shouldShow()) {
            switch (towerSelectionWheel.checkPress(touchPoint)) {
                case 0:
                    //nothing selected
                    plotTouched = false;
                    break;
                case 1:
                    //a tower was taped
                    plotTouched = true;
                    break;
            }
        }

        //check for plot selection
        for (PlotHandler plotHandler : plotHandlers) {
            if (plotHandler.checkTouch(touchPoint)) {
                plotTouched = true;

                if (plotHandler.isAvailable()) {
                    plotInFocus = plotHandler.getArrayPos();
                    towerSelectionWheel.setLocation(plotHandler.getLocation(), infoBuyPage);
                    infoBuyPage.setShowMe(false);
                    infoUpgradePage.setShowMe(false);
                    towerSelectionWheel.setShowMe(true);
                } else {
                    infoUpgradePage.setData(plotHandler);
                    infoUpgradePage.setShowMe(true);
                }
            }
        }

        if (!plotTouched) {
            infoBuyPage.setShowMe(false);
            infoUpgradePage.setShowMe(false);
            towerSelectionWheel.setShowMe(false);
        }
    }

    long lastShotTime = 0;
    public void addTestEnemy(){
        long curTime = System.currentTimeMillis();

        if(curTime - lastShotTime >= 2000){
            lastShotTime = curTime;
            TestRect testRect = new TestRect(
                    (int) Tools.convertDpToPixel(165),
                    (int) Tools.convertDpToPixel(800),
                    screenSize);
            targets.add(testRect);
        }
    }

    MapOne mapOne;

    private void setMapData() {
        mapOne = new MapOne(screenSize, getResources(), getContext());
        plotHandlers = mapOne.getMapOnePlots();
        for(PlotHandler handler: plotHandlers){
            handler.setScreenSize(screenSize);
        }
    }

    public void stopRunning() {
        running = false;
    }

    public void cleanUp() {
        running = false;
    }

    public PlotHandler plotInPos(int pos) {
        for (PlotHandler plotHandler : plotHandlers) {
            if (pos == plotHandler.getArrayPos()) {
                return plotHandler;
            }
        }
        return null;
    }

    public void resume() {
        this.running = true;
        Thread thread = new Thread(this);
        thread.start();
    }
}

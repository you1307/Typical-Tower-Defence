package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import com.thetechnoobs.typicaltowerdefence.enemys.EasyFastEnemy;
import com.thetechnoobs.typicaltowerdefence.enemys.EasySlowEnemy;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.maps.MapBase;
import com.thetechnoobs.typicaltowerdefence.maps.MapOne;
import com.thetechnoobs.typicaltowerdefence.maps.MapThree;
import com.thetechnoobs.typicaltowerdefence.maps.MapTwo;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;
import com.thetechnoobs.typicaltowerdefence.ui.CoinHeader;
import com.thetechnoobs.typicaltowerdefence.ui.InfoBuyPage;
import com.thetechnoobs.typicaltowerdefence.ui.InfoUpgradePage;
import com.thetechnoobs.typicaltowerdefence.ui.TowerBuySelectWheel;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    boolean running = true;
    private int frames;
    private double delta;
    ArrayList<PlotHandler> plotHandlers = new ArrayList<>();
    int plotInFocus;
    int[] screenSize;
    Canvas canvas;
    CoinHeader coinHeader;
    int mapToLoad = 0;
    TowerBuySelectWheel towerSelectionWheel;
    InfoBuyPage infoBuyPage;
    InfoUpgradePage infoUpgradePage;
    ArrayList<EnemyBase> targets = new ArrayList<>();

    public GameView(Context context, int[] screenSize, int mapToLoad) {
        super(context);
        this.mapToLoad = mapToLoad;
        this.screenSize = screenSize;
        coinHeader = new CoinHeader(getResources(), context);

        setMapData(mapToLoad);

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
        if(targets.size() < 4){
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

            if(infoUpgradePage.shouldShow() && plotInFocus == plotHandler.getPlotID()){
                plotHandler.setShowRange(true);
                infoUpgradePage.update();
            }else{
                plotHandler.setShowRange(false);
            }
        }
    }

    private void updateGraphics() {
        if (getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            selectedMap().draw(canvas);

            coinHeader.draw(canvas);

            for(EnemyBase t: targets){
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

        //check if buy button is pushed from buy pannel
        if (infoBuyPage.shouldShow() && infoBuyPage.buyButtonPushed(touchPoint)) {
            plotInPos(plotInFocus).setTowerType(infoBuyPage.getLoadedTower());
            infoBuyPage.setShowMe(false);
            towerSelectionWheel.setShowMe(false);
            return;
        }

        //check if remove button was pushed while info upgrade page is bing shown
        if(infoUpgradePage.shouldShow() && infoUpgradePage.removeBtnPushed(touchPoint)){
            for(PlotHandler plotHandler: plotHandlers){
                if(plotInFocus == plotHandler.getPlotID()){
                    plotHandler.removeTower();
                }
            }
            infoUpgradePage.setShowMe(false);
        }

        //check if upgrade button is pushed while info upgrade page is showing
        if(infoUpgradePage.shouldShow() && infoUpgradePage.upgradeButtonPushed(touchPoint)){
            for(PlotHandler plotHandler: plotHandlers){
                if(plotInFocus == plotHandler.getPlotID()){
                    plotHandler.upgradeTowerOneLevel();
                    infoUpgradePage.setData(plotHandler);
                    infoUpgradePage.setShowMe(true);
                    return;
                }
            }
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
                    towerSelectionWheel.setLocation(plotHandler.getLocation(), infoBuyPage);
                    infoBuyPage.setShowMe(false);
                    infoUpgradePage.setShowMe(false);
                    towerSelectionWheel.setShowMe(true);
                } else {
                    infoUpgradePage.setData(plotHandler);
                    infoUpgradePage.setShowMe(true);
                }
                plotInFocus = plotHandler.getPlotID();
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

        if(curTime - lastShotTime >= 500){
            lastShotTime = curTime;
            EasySlowEnemy easySlowEnemy = new EasySlowEnemy(
                    (int) Tools.convertDpToPixel(165),
                    screenSize[1] + 50,
                    screenSize, selectedMap().enemyPathPoints());

            EasyFastEnemy easyFastEnemy = new EasyFastEnemy(
                    (int) Tools.convertDpToPixel(165),
                    screenSize[1] + 50,
                    screenSize, selectedMap().enemyPathPoints());


            targets.add(easySlowEnemy);
        }
    }

    MapOne mapOne;
    MapTwo mapTwo;
    MapThree mapThree;
    private void setMapData(int mapToLoad) {
        switch (mapToLoad){
            case 0:
                Toast.makeText(getContext(), "Error loading selected map", Toast.LENGTH_LONG).show();
                return;
            case 1:
                mapOne = new MapOne(screenSize, getResources(), getContext());
                plotHandlers = mapOne.getMapOnePlots();
                break;
            case 2:
                mapTwo = new MapTwo(screenSize, getResources(), getContext());
                plotHandlers = mapTwo.getMapTwoPlots();
                break;
            case 3:
                mapThree = new MapThree(screenSize, getResources(), getContext());
                plotHandlers = mapThree.getMapThreePlots();
                break;
        }

        for(PlotHandler handler: plotHandlers){
            handler.setScreenSize(screenSize);
        }
    }

    public MapBase selectedMap(){
        switch (mapToLoad){
            case 1:
                return mapOne;
            case 2:
                return mapTwo;
            case 3:
                return mapThree;
            default:
                return null;
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
            if (pos == plotHandler.getPlotID()) {
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

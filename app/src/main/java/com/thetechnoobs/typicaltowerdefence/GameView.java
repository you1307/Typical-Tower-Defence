package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import com.thetechnoobs.typicaltowerdefence.ui.WaveInfoHeader;

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
    WaveInfoHeader waveInfoHeader;
    int mapToLoad = 0;
    TowerBuySelectWheel towerSelectionWheel;
    InfoBuyPage infoBuyPage;
    InfoUpgradePage infoUpgradePage;
    UserData userData;
    ArrayList<EnemyBase> targets = new ArrayList<>();
    private boolean readyForWave = false;

    public GameView(Context context, int[] screenSize, int mapToLoad) {
        super(context);
        userData = new UserData();
        this.mapToLoad = mapToLoad;
        this.screenSize = screenSize;
        coinHeader = new CoinHeader(getResources(), context, userData);
        waveInfoHeader = new WaveInfoHeader();

        setMapData(mapToLoad);

        infoBuyPage = new InfoBuyPage(context, userData);
        infoUpgradePage = new InfoUpgradePage(context, userData);
        towerSelectionWheel = new TowerBuySelectWheel(getResources(), infoBuyPage);

    }

    @Override
    public void run() {
        while (running) {
            long lastTime = System.nanoTime();
            final double amountOfTicks = 60.00;
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

    long lastEnemySpawnTime = 0;
    int enemyIterationVar = 0;

    private void tick() {
        waveInfoHeader.update();

        long curSysTime = System.currentTimeMillis();
        if (readyForWave && curSysTime - lastEnemySpawnTime > 1000) {
            if(enemyIterationVar >= tempEnemysArray.size()){
                enemyIterationVar = 0;
                readyForWave = false;
            }else{
                targets.add(tempEnemysArray.get(enemyIterationVar));
                enemyIterationVar++;
                lastEnemySpawnTime = curSysTime;
            }
        }

        for (int t = 0; t < targets.size(); t++) {
            if (targets.get(t).getHeath() <= 0) {
                userData.addCoins(targets.get(t).getDeathReward());
            }

            if (targets.get(t).shouldRemove()) {
                targets.remove(t);
            } else {
                targets.get(t).update();
            }
        }

        for (PlotHandler plotHandler : plotHandlers) {
            plotHandler.update(targets);

            if (infoUpgradePage.shouldShow() && plotInFocus == plotHandler.getPlotID()) {
                plotHandler.setShowRange(true);
                infoUpgradePage.update();
            } else {
                plotHandler.setShowRange(false);
            }


            //update buy pannel if its showing
            if (infoBuyPage.shouldShow()) {
                infoBuyPage.update();
            }
        }
    }

    private void updateGraphics() {
        if (getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();

            selectedMap().draw(canvas);

            coinHeader.draw(canvas);
            waveInfoHeader.draw(canvas);

            for (int t = 0; t < targets.size(); t++) {
                targets.get(t).draw(canvas);
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

            if (infoUpgradePage.shouldShow()) {
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

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            presedDown(touchPoint);
        }

        return true;
    }

    ArrayList<EnemyBase> tempEnemysArray = new ArrayList<>();

    private void presedDown(RectF touchPoint) {
        boolean plotTouched = false;

        //if user tapes send wave button
        if (waveInfoHeader.sendWaveButtonPushed(touchPoint)) {
            targets.add(new EasySlowEnemy(screenSize[0]/2, screenSize[1], screenSize, selectedMap().enemyPathPoints(), getResources()));
            readyForWave = true;
            return;
        }

        //check if buy button is pushed from buy pannel while is being shown
        if (infoBuyPage.shouldShow() && infoBuyPage.buyButtonPushed(touchPoint)) {
            if (userData.getUserCoins() >= infoBuyPage.getPrice()) {
                userData.removeCoins(infoBuyPage.getPrice());
                plotInPos(plotInFocus).setTowerType(infoBuyPage.getLoadedTower());
                infoBuyPage.setShowMe(false);
                towerSelectionWheel.setShowMe(false);
            } else {
                infoBuyPage.setShowMe(true);
                towerSelectionWheel.setShowMe(true);
            }
            return;
        }

        //check if remove button was pushed while info upgrade page is bing shown
        if (infoUpgradePage.shouldShow() && infoUpgradePage.removeBtnPushed(touchPoint)) {
            for (PlotHandler plotHandler : plotHandlers) {
                if (plotInFocus == plotHandler.getPlotID()) {
                    plotHandler.removeTower();
                }
            }
            infoUpgradePage.setShowMe(false);
        }

        //check if upgrade button is pushed while info upgrade page is showing
        if (infoUpgradePage.shouldShow() && infoUpgradePage.upgradeButtonPushed(touchPoint)) {
            for (PlotHandler plotHandler : plotHandlers) {
                if (plotInFocus == plotHandler.getPlotID()) {
                    if (userData.getUserCoins() >= infoUpgradePage.getPrice()) {
                        userData.removeCoins(infoUpgradePage.getPrice());
                        plotHandler.upgradeTowerOneLevel();
                    }
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

    MapOne mapOne;
    MapTwo mapTwo;
    MapThree mapThree;

    private void setMapData(int mapToLoad) {
        switch (mapToLoad) {
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

        for (PlotHandler handler : plotHandlers) {
            handler.setScreenSize(screenSize);
        }
    }

    public MapBase selectedMap() {
        switch (mapToLoad) {
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
        cleanUp();
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

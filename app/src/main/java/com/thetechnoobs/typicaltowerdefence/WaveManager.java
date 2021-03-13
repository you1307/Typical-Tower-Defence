package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;

import com.thetechnoobs.typicaltowerdefence.enemys.EasySlowEnemy;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;

public class WaveManager implements Runnable {
    Context context;
    GameView gameView;
    int[] screenSize;
    boolean runWaveManager = true;

    WaveManager(Context context, GameView gameView, int[] screenSize){
        this.context = context;
        this.screenSize = screenSize;
        this.gameView = gameView;
    }


    public EnemyBase getWave(){
        EasySlowEnemy easySlowEnemy = new EasySlowEnemy(
                (int) Tools.convertDpToPixel(165),
                screenSize[1] + 20,
                screenSize, gameView.selectedMap().enemyPathPoints(), context.getResources());

        return easySlowEnemy;
    }

    @Override
    public void run() {
        while (runWaveManager){
            if(gameView.targets.size() < 5){
                gameView.addTestEnemy(getWave());
            }
        }
    }

    public void stop(){
        runWaveManager = false;
    }
}

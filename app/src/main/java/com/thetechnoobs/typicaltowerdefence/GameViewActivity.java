package com.thetechnoobs.typicaltowerdefence;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameViewActivity extends AppCompatActivity {
    GameView gameView;
    Thread gameThread;
    int mapToLoad = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mapToLoad = getIntent().getIntExtra("map", mapToLoad);

        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        int[] screenSize = {point.x, point.y};


        gameView = new GameView(this, screenSize, mapToLoad);
        gameThread = new Thread(gameView);

        setContentView(gameView);

        //startThread();
    }

    private void startThread(){
        gameThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.cleanUp();
        gameView.stopRunning();
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        gameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.cleanUp();
        gameView.stopRunning();
        try {
            gameThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

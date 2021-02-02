 package com.thetechnoobs.typicaltowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

 public class MainActivity extends AppCompatActivity {
    GameView gameView;
    Thread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        int[] screenSize = {point.x, point.y};

        gameView = new GameView(this, screenSize);
        gameThread = new Thread(gameView);

        setContentView(gameView);

        //startThread();
    }

     private void startThread() {
        gameThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.cleanUp();
        gameView.stopRunning();
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
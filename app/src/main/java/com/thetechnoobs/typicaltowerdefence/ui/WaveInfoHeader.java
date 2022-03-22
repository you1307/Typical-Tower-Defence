package com.thetechnoobs.typicaltowerdefence.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;

public class WaveInfoHeader {

    RectF sendBTN;
    Paint testPaint = new Paint();
    Paint enemysLoadedText = new Paint();
    int nextWaveEnemyCount = 0;

    public WaveInfoHeader(){
        enemysLoadedText.setTextSize(Tools.convertDpToPixel(15));
        iniButtons();
    }

    private void iniButtons() {
        sendBTN = new RectF(
                Tools.convertDpToPixel(200),
                Tools.convertDpToPixel(15),
                Tools.convertDpToPixel(350),
                Tools.convertDpToPixel(50));
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawRect(sendBTN, testPaint);
        canvas.drawText(String.valueOf(nextWaveEnemyCount), sendBTN.centerX(), sendBTN.centerY(), enemysLoadedText);
    }

    public boolean sendWaveButtonPushed(RectF touchPoint){
        return touchPoint.intersect(sendBTN);
    }
}

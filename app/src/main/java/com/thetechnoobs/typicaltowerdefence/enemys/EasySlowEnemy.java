package com.thetechnoobs.typicaltowerdefence.enemys;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.ArrayList;
import java.util.Random;

public class EasySlowEnemy extends EnemyBase {
    double distanceMoved = 0;

    public EasySlowEnemy(int x, int y, int[] screenSize, ArrayList<RectF> mapPath) {
        setCurX(x);
        setCurY(y);
        this.mapPath = mapPath;
        this.screenSize = screenSize;

        this.speed = Tools.convertDpToPixel(1f);

        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize(Tools.convertDpToPixel(20));
    }

    @Override
    public void update() {
        super.update();

        if (timesHit > 9) {
            removeMe = true;
        }

        if(offScreen()){
            removeMe = true;
        }
    }


    public double getDistanceMoved() {
        return distanceMoved;
    }

    @Override
    public boolean shouldRemove() {
        return removeMe;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

            debugPaint.setColor(Color.RED);
            canvas.drawRect(getHitbox(), debugPaint);

            debugPaint.setColor(Color.BLACK);
            canvas.drawText(
                    String.valueOf(timesHit),
                    getHitbox().centerX(),
                    getHitbox().centerY(),
                    debugPaint);
    }

    public RectF getHitbox() {
        return new RectF(getCurX(), getCurY(), getCurX() + 40, getCurY() + 40);
    }
}



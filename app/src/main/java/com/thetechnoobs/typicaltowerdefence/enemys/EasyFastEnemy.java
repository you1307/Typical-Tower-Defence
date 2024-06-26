package com.thetechnoobs.typicaltowerdefence.enemys;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.ArrayList;

public class EasyFastEnemy extends EnemyBase {
    double distanceMoved = 0;
    Paint debugPaint = new Paint();

    public EasyFastEnemy(int x, int y, int[] screenSize, ArrayList<RectF> mapPath) {
        setCurX(x);
        setCurY(y);
        this.mapPath = mapPath;
        this.screenSize = screenSize;

        this.speed = Tools.convertDpToPixel(2f);

        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize((float) Tools.convertDpToPixel(20));
    }

    @Override
    public void update() {
        super.update();

        if(offScreen()){
            removeMe = true;
        }
    }

    @Override
    public void updateSpriteFrame() {

    }


    public double getDistanceMoved() {
        return distanceMoved;
    }

    @Override
    public int getHeath() {
        return curHeath;
    }

    @Override
    public int getDeathReward() {
        return 7;
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
    }

    public RectF getHitbox() {
        return new RectF(getCurX(), getCurY(), getCurX() + 40, getCurY() + 40);
    }
}

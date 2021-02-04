package com.thetechnoobs.typicaltowerdefence.enemys;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;

public class TestRect {

    int x, y;
    int[] screenSize;
    int timesHit = 0;
    double distanceMoved = 0;
    boolean removeMe = false;
    Paint debugPaint = new Paint();

    public TestRect(int x, int y, int[] screenSize) {
        this.x = x;
        this.y = y;
        this.screenSize = screenSize;

        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize(Tools.convertDpToPixel(20));
    }

    public void update() {
        if(timesHit > 9){
            removeMe = true;
        }
        move();
    }

    private void move() {
        y -= Tools.convertDpToPixel(0.8f);
        distanceMoved += Tools.convertDpToPixel(0.8f);

        if (y < 0) {
            removeMe = true;
        }
    }

    public void addTimeHit(){
        timesHit++;
    }

    public double getDistanceMoved(){
        return distanceMoved;
    }

    public boolean shouldRemove(){
        return removeMe;
    }

    public void draw(Canvas canvas) {
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
        return new RectF(x, y, x + 40, y + 40);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}



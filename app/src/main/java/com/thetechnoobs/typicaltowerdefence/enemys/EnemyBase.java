package com.thetechnoobs.typicaltowerdefence.enemys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.ArrayList;

public abstract class EnemyBase {

    int curX, curY;
    double speed = Tools.convertDpToPixel(1f);
    double distanceMoved = 0;
    boolean removeMe = false;
    int MAX_HEATH = 10;
    int curHeath = MAX_HEATH;
    Paint debugPaint = new Paint();
    int[] screenSize;
    float xVelocity, yVelocity;
    Paint heathBarPaint = new Paint();
    ArrayList<RectF> mapPath;
    public int direction = 1;
    Resources resources;

    public int getCurX() {
        return curX;
    }

    public void setCurX(int curX) {
        this.curX = curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurY(int curY) {
        this.curY = curY;
    }

    public void addDamage(int damage) {
        curHeath -= damage;
    }

    public boolean shouldRemove() {
        if (offScreen() || curHeath <= 0) {
            return true;
        } else {
            return removeMe;
        }
    }

    public void draw(Canvas canvas) {

        switch (direction) {
            case 1:
                debugPaint.setColor(Color.BLUE);
                break;
            case 2:
                debugPaint.setColor(Color.BLACK);
                break;
            case 3:
                debugPaint.setColor(Color.RED);
                break;
            case 4:
                debugPaint.setColor(Color.GREEN);
                break;
        }


        drawHeathBar(canvas);

        for (int i = 0; i < mapPath.size(); i++) {
            canvas.drawRect(mapPath.get(i), debugPaint);
        }
    }

    private void drawHeathBar(Canvas canvas) {
        RectF healthBackgroundBar = new RectF(getCurX(), getCurY() - Tools.convertDpToPixel(5), getCurX() + Tools.convertDpToPixel(MAX_HEATH), getCurY() - Tools.convertDpToPixel(8));
        RectF curHeathBarForground = new RectF(getCurX(), getCurY() - Tools.convertDpToPixel(5), getCurX() + Tools.convertDpToPixel(curHeath), getCurY() - Tools.convertDpToPixel(8));

        heathBarPaint.setColor(Color.RED);
        canvas.drawRect(healthBackgroundBar, heathBarPaint);

        heathBarPaint.setColor(Color.GREEN);
        canvas.drawRect(curHeathBarForground, heathBarPaint);
    }

    public void update() {
        getDirection();
        animation();
        move();
    }

    public double getAngle(double x, double y) {
        return Math.atan2(y, x) * (180 / Math.PI);
    }

    public void getDirection() {
        //1 = left, 2 = up, 3 = right, 4 = down
        double angle = getAngle(xVelocity, yVelocity);

        if (angle < -39 && angle > -100) {
            //if moving up
            direction = 2;
        } else if (angle > 140) {
            //if moving left
            direction = 1;
        } else if (angle > 0.2 && angle < 1.0) {
            //moving right
            direction = 3;
        } else {
            //direction = 4;
        }

        Log.v("vel", "angle: " + getAngle(xVelocity, yVelocity) + "----Direction: " + direction);
    }

    boolean walking = true;
    long lastAnimationMoveTime = 0;
    int curSpriteFrame = 1;
    long spriteFrameWaitTimeMilliseconds = 100;

    private void animation() {
        long curTime = System.currentTimeMillis();

        if (walking && curTime - lastAnimationMoveTime >= spriteFrameWaitTimeMilliseconds) {
            curSpriteFrame++;
            lastAnimationMoveTime = curTime;
        }
        updateSpriteFrame();
    }

    int targetPointsReached = 0;

    private void move() {
        setCurX((int) (getCurX() + speed * xVelocity));
        setCurY((int) (getCurY() + speed * yVelocity));

        distanceMoved += (getHitbox().centerX() + speed * xVelocity) + (getHitbox().centerY() + speed * yVelocity);

        if (atTargetPoint(mapPath.get(targetPointsReached))) {
            targetPointsReached++;
        }

        if (targetPointsReached == mapPath.size()) {
            targetPointsReached = 0;
        }

        calculateDirection(mapPath.get(targetPointsReached));
    }

    public boolean atTargetPoint(RectF targetPoint) {
        if (getHitbox().intersect(targetPoint)) {
            return true;
        } else {
            return false;
        }
    }

    public void calculateDirection(RectF targetPoint) {
        float targetX = targetPoint.centerX();
        float targetY = targetPoint.centerY();

        float totalAllowedMovment = 1.0f;
        float xDistanceFromTarget = Math.abs(targetX - getHitbox().centerX());
        float yDistanceFromTarget = Math.abs(targetY - getHitbox().centerY());
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovment = xDistanceFromTarget / totalDistanceFromTarget;

        xVelocity = xPercentOfMovment;
        yVelocity = totalAllowedMovment - xPercentOfMovment;

        if (targetX < getHitbox().centerX()) {
            xVelocity *= -1;
        }

        if (targetY < getHitbox().centerY()) {
            yVelocity *= -1;
        }
    }

    public boolean offScreen() {
        if (getCurY() < 0 || getCurY() > screenSize[1] + 100 || getCurX() < 0 || getCurX() > screenSize[0]) {
            return true;
        } else {
            return false;
        }
    }

    public RectF getHitbox() {
        return null;
    }

    public double getDistanceMoved() {
        return 0;
    }

    public abstract int getHeath();
    public abstract int getDeathReward();
    public abstract void updateSpriteFrame();
}

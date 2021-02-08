package com.thetechnoobs.typicaltowerdefence.enemys;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.projectials.CannonBall;

import java.util.ArrayList;

public class EnemyBase {
    int curX, curY;
    float speed = Tools.convertDpToPixel(1f);
    double distanceMoved = 0;
    boolean removeMe = false;
    Paint debugPaint = new Paint();
    int[] screenSize;
    float xVelocity, yVelocity;
    ArrayList<RectF> mapPath;

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

    int timesHit = 0;

    public void addTimeHit(){
        timesHit++;
    }

    public boolean shouldRemove(){
        return false;
    }

    public void draw(Canvas canvas){
//        for(int i = 0; i <mapPath.size(); i++){
//            canvas.drawRect(mapPath.get(i), debugPaint);
//        }
    }

    public void update(){
        move();
    }

    int targetPointsReached = 0;
    private void move() {
        calculateDirection(mapPath.get(targetPointsReached));
        setCurX((int) (getCurX() + speed * xVelocity));
        setCurY((int) (getCurY() + speed * yVelocity));

        distanceMoved += (getCurX() + speed * xVelocity) + (getCurY() + speed * yVelocity);

        if(atTargetPoint(mapPath.get(targetPointsReached))){
            targetPointsReached++;
        }

        if(targetPointsReached == mapPath.size()){
            targetPointsReached = 0;
        }
    }

    public boolean atTargetPoint(RectF targetPoint) {
        if(getHitbox().intersect(targetPoint)){
            return true;
        }else{
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

    public boolean offScreen(){
        if(getCurY() < 0 || getCurY() > screenSize[1] + 100 || getCurX() < 0 || getCurX() > screenSize[0]){
            return true;
        }else{
            return false;
        }
    }

    public RectF getHitbox() {
        return null;
    }

    public double getDistanceMoved() {
        return 0;
    }
}

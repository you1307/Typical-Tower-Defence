package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;

import java.util.ArrayList;

public abstract class TowerBase {

    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    boolean hasTarget = false;
    Context context;
    ArrayList<EnemyBase> targets = new ArrayList<>();
    EnemyBase focusedTarget;
    int SpriteWidth;
    int SpriteHeight;
    boolean showRange = false;
    int SpriteFrameLocX = 1;
    int SpriteFrameLocY = 3;
    int[] screenSize;
    Paint testPaint = new Paint();
    boolean playingShootAnimation = false;

    public void draw(Canvas canvas) {
        int x = SpriteWidth * SpriteFrameLocX - SpriteWidth;
        int y = SpriteHeight * SpriteFrameLocY - SpriteHeight;

        frame = new Rect(x, y, x + SpriteWidth, y + SpriteHeight);

        if (playingShootAnimation) {
            shootAnimation();
        }

        if(showRange){
            drawRangBox(canvas);
        }

        canvas.drawBitmap(towerBitmap, frame, location, null);
    }

    public EnemyBase updateTargetFirstInLine(double maxRange) {
        float[] curPositionCenter = {location.centerX(), location.centerY()};

        for(EnemyBase enemy: targets){
            if(enemy.getHitbox().intersect(getRangeBox())){
                float[] enemyLocationCenter = {enemy.getHitbox().centerX(), enemy.getHitbox().centerY()};
                focusedTarget = enemy;

                if(getDistanceToEnemy(curPositionCenter, enemyLocationCenter) < maxRange
                        && enemy.getDistanceMoved() >= focusedTarget.getDistanceMoved()){

                    focusedTarget = enemy;
                    hasTarget = true;
                    return focusedTarget;
                }else{
                    focusedTarget = null;
                    hasTarget = false;
                }
            }
        }

        return null;
    }

    private void drawRangBox(Canvas canvas) {
        float[] corners = new float[]{
                Tools.convertDpToPixel(360), Tools.convertDpToPixel(360),      // Top left radius in px
                Tools.convertDpToPixel(360), Tools.convertDpToPixel(360),      // Top right radius in px
                Tools.convertDpToPixel(360), Tools.convertDpToPixel(360),      // Bottom right radius in px
                Tools.convertDpToPixel(360), Tools.convertDpToPixel(360)       // Bottom left radius in px
        };

        final Path path = new Path();
        path.addRoundRect(getRangeBox(), corners, Path.Direction.CW);
        canvas.drawPath(path, testPaint);
    }

    public void setShowRange(boolean shouldShow){
            showRange = shouldShow;
    }

    public void update(){}

    public double getDistanceToEnemy(float[] curXY, float[] targetXY) {
        double ac = Math.abs(targetXY[1] - curXY[1]);
        double cb = Math.abs(targetXY[0] - curXY[0]);

        return Math.hypot(ac, cb);
    }

    public abstract RectF getRangeBox();
    protected abstract void shoot();
    protected abstract void shootAnimation();
}

package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.TestRect;
import com.thetechnoobs.typicaltowerdefence.projectials.Arrow;
import com.thetechnoobs.typicaltowerdefence.projectials.CannonBall;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.CannonTowerData;

import java.util.ArrayList;

public class CannonTower {

    private final Context context;
    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    boolean shooting = false;
    private boolean playingShootAnimation = false;
    CannonTowerData cannonTowerData;
    ArrayList<TestRect> targets = new ArrayList<>();
    TestRect focusedTarget;
    Paint testPaint = new Paint();
    ArrayList<CannonBall> cannonBalls = new ArrayList<>();
    int[] screenSize;
    int cannonSpriteWidth;
    int cannonSpriteHeight;
    int cannonSpriteFrameLocX = 1;
    int cannonSpriteFrameLocY = 1;

    public CannonTower(Context context, RectF location, CannonTowerData cannonTowerData, int[] screenSize) {
        resources = context.getResources();
        this.cannonTowerData = cannonTowerData;
        this.screenSize = screenSize;
        this.context = context;
        this.location = location;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    public void draw(Canvas canvas){
        int x = cannonSpriteWidth * cannonSpriteFrameLocX - cannonSpriteWidth;
        int y = cannonSpriteHeight * cannonSpriteFrameLocY - cannonSpriteHeight;

        frame = new Rect(x, y, x + cannonSpriteWidth, y + cannonSpriteHeight);

        if(playingShootAnimation){
            shootAnimation();
        }

        for(CannonBall cannonBall: cannonBalls){
            cannonBall.draw(canvas);
        }

        drawRangBox(canvas);

        canvas.drawBitmap(towerBitmap, frame, location, null);
    }

    long timeLastShot = 0;
    public void update(){
        updateTarget();

        if (hasTarget && !focusedTarget.shouldRemove()) {
            long newShotTime = System.currentTimeMillis();
            if (newShotTime - timeLastShot >= 500) {
                shoot();
                timeLastShot = newShotTime;
            }
        }


        for (CannonBall cannonBall : cannonBalls) {
            if (cannonBall.shouldRemove()) {
                cannonBalls.remove(cannonBall);
                break;
            } else {
                cannonBall.update();
            }
        }

    }

    private void drawRangBox(Canvas canvas) {
        float[] corners = new float[]{
                Tools.convertDpToPixel(100), Tools.convertDpToPixel(100),      // Top left radius in px
                Tools.convertDpToPixel(100), Tools.convertDpToPixel(100),      // Top right radius in px
                Tools.convertDpToPixel(100), Tools.convertDpToPixel(100),      // Bottom right radius in px
                Tools.convertDpToPixel(100), Tools.convertDpToPixel(100)       // Bottom left radius in px
        };

        final Path path = new Path();
        path.addRoundRect(getRangeBox(), corners, Path.Direction.CW);
        canvas.drawPath(path, testPaint);
    }

    private void shoot() {
        playingShootAnimation = true;
        CannonBall newBall = new CannonBall(
                (int) location.centerX(),
                (int) location.centerY(),
                context,
                focusedTarget,
                screenSize);
        cannonBalls.add(newBall);
    }

    public void updateTargets(ArrayList<TestRect> targets) {
        this.targets = targets;
    }

    long ticks = 0;

    public void shootAnimation() {
        ticks++;

        if (ticks % 4 == 1) {
            cannonSpriteFrameLocX++;

            if (cannonSpriteFrameLocX == 8) {
                cannonSpriteFrameLocX = 1;
                playingShootAnimation = false;
                ticks = 0;
            }
        }
    }

    boolean hasTarget = false;
    TestRect lastCheck = null;
    private void updateTarget() {
        Log.v("testing", "size: "+ targets.size());

        for (TestRect enemy : targets) {
            if(lastCheck == null){
                lastCheck = enemy;
            }

            if(enemy.getHitbox().intersect(getRangeBox()) && !enemy.shouldRemove()){
                if(enemy.getDistanceMoved() >= lastCheck.getDistanceMoved()){
                    focusedTarget = enemy;
                    hasTarget = true;
                }else if(enemy.getDistanceMoved() < lastCheck.getDistanceMoved() && lastCheck.getHitbox().intersect(getRangeBox())){
                    focusedTarget = lastCheck;
                    hasTarget = true;
                }else if(enemy.getHitbox().intersect(getRangeBox())){
                    focusedTarget = enemy;
                    hasTarget = true;
                }else{
                    hasTarget = false;
                }
            }

            lastCheck = enemy;
        }

        if(focusedTarget != null && !focusedTarget.getHitbox().intersect(getRangeBox())){
            focusedTarget = null;
            hasTarget = false;
        }
    }

    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.left - ((cannonTowerData.getRange() / 2) * 100),
                (int) location.top - ((cannonTowerData.getRange() / 2) * 60),
                (int) (location.left + location.width() + ((cannonTowerData.getRange() / 2) * 100)),
                (int) (location.top + location.height() + ((cannonTowerData.getRange() / 2) * 110)));
        return tempR;
    }


    RectF rangeHitbox;

    private void settupRangeHitbox() {
        Rect temprec = new Rect(400, 400, 450, 450);
        rangeHitbox = new RectF();
        rangeHitbox.round(temprec);
    }

    private void setupBitmap(){
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        cannonSpriteWidth = towerBitmap.getWidth()/8;
        cannonSpriteHeight = towerBitmap.getHeight()/4;
    }
}

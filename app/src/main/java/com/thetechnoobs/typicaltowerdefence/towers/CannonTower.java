package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.projectials.CannonBall;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.CannonTowerData;

import java.util.ArrayList;

public class CannonTower extends TowerBase {

    CannonTowerData cannonTowerData;
    ArrayList<CannonBall> cannonBalls = new ArrayList<>();

    public CannonTower(Context context, RectF location, CannonTowerData cannonTowerData, int[] screenSize) {
        resources = context.getResources();
        this.cannonTowerData = cannonTowerData;
        this.screenSize = screenSize;
        this.context = context;
        this.location = location;

        this.SpriteFrameLocY = 1;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (CannonBall cannonBall : cannonBalls) {
            cannonBall.draw(canvas);
        }
    }

    long timeLastShot = 0;

    public void update() {
        focusedTarget = updateTargetFirstInLine(cannonTowerData.getRange());

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

    @Override
    public void shoot() {
        playingShootAnimation = true;
        CannonBall newBall = new CannonBall(
                (int) location.centerX(),
                (int) location.top,
                context,
                focusedTarget,
                screenSize, cannonTowerData.getDamage());
        cannonBalls.add(newBall);
    }

    public void updateTargets(ArrayList<EnemyBase> targets) {
        this.targets = targets;
    }

    long ticks = 0;
    public void shootAnimation() {
        ticks++;

        if (ticks % 4 == 1) {
            SpriteFrameLocX++;

            if (SpriteFrameLocX == 8) {
                SpriteFrameLocX = 1;
                playingShootAnimation = false;
                ticks = 0;
            }
        }
    }

    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.centerX() - (cannonTowerData.getRange()),
                (int) location.centerY() - (cannonTowerData.getRange()),
                (int) (location.centerX() + (cannonTowerData.getRange())),
                (int) (location.centerY() + (cannonTowerData.getRange())));

        return tempR;
    }


    RectF rangeHitbox;

    private void settupRangeHitbox() {
        Rect temprec = new Rect(400, 400, 450, 450);
        rangeHitbox = new RectF();
        rangeHitbox.round(temprec);
    }

    private void setupBitmap() {
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        SpriteWidth = towerBitmap.getWidth() / 8;
        SpriteHeight = towerBitmap.getHeight() / 4;
    }
}

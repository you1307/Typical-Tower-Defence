package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.projectials.Arrow;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;

import java.util.ArrayList;

public class ArrowTower extends TowerBase {

    ArrowTowerData arrowTowerData;
    ArrayList<Arrow> arrows = new ArrayList<>();

    public ArrowTower(Context context, RectF location, ArrowTowerData arrowTowerData, int[] screenSize) {
        resources = context.getResources();
        this.context = context;
        this.screenSize = screenSize;
        this.location = location;
        this.arrowTowerData = arrowTowerData;

        this.SpriteFrameLocY = 3;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (Arrow arrow : arrows) {
            arrow.draw(canvas);
        }
    }

    long timeLastShot = 0;

    public void update() {
        focusedTarget = updateTargetFirstInLine(arrowTowerData.getRange());

        if (hasTarget && !focusedTarget.shouldRemove()) {
            long newShotTime = System.currentTimeMillis();
            if (newShotTime - timeLastShot >= 500) {
                shoot();
                timeLastShot = newShotTime;
            }
        }


        for (Arrow arrow : arrows) {
            if (arrow.shouldRemove()) {
                arrows.remove(arrow);
                break;
            } else {
                arrow.update();
            }
        }

    }

    public void updateTargets(ArrayList<EnemyBase> targets) {
        this.targets = targets;
    }

    @Override
    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.centerX() - (arrowTowerData.getRange()),
                (int) location.centerY() - (arrowTowerData.getRange()),
                (int) (location.centerX() + (arrowTowerData.getRange())),
                (int) (location.centerY() + (arrowTowerData.getRange())));

        return tempR;
    }

    @Override
    protected void shoot() {
        playingShootAnimation = true;
        Arrow newArrow = new Arrow(
                (int) location.centerX(),
                (int) location.top,
                context,
                focusedTarget,
                screenSize, arrowTowerData.getDamage());
        arrows.add(newArrow);
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

    RectF rangeHitbox;

    private void settupRangeHitbox() {
        Rect temprec = new Rect(500, 500, 550, 550);
        rangeHitbox = new RectF();
        rangeHitbox.round(temprec);
    }

    private void setupBitmap() {
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        SpriteWidth = towerBitmap.getWidth() / 8;
        SpriteHeight = towerBitmap.getHeight() / 4;
    }
}

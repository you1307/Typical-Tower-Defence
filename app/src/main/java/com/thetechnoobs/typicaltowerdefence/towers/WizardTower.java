package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.projectials.WizardOrb;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.WizardTowerData;

import java.util.ArrayList;

public class WizardTower extends TowerBase {

    WizardTowerData wizardTowerData;
    ArrayList<EnemyBase> targets = new ArrayList<>();
    ArrayList<WizardOrb> orbs = new ArrayList<>();

    public WizardTower(Context context, RectF location, WizardTowerData wizardTowerData, int[] screenSize) {
        resources = context.getResources();
        this.context = context;
        this.screenSize = screenSize;
        this.location = location;
        this.wizardTowerData = wizardTowerData;

        this.SpriteFrameLocY = 4;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (WizardOrb orb : orbs) {
            orb.draw(canvas);
        }
    }

    long timeLastShot = 0;

    public void update() {
        updateTarget();

        if (hasTarget && !focusedTarget.shouldRemove()) {
            long newShotTime = System.currentTimeMillis();
            if (newShotTime - timeLastShot >= 500) {
                shoot();
                timeLastShot = newShotTime;
            }
        }


        for (WizardOrb orb : orbs) {
            if (orb.shouldRemove()) {
                orbs.remove(orb);
                break;
            } else {
                orb.update();
            }
        }

    }

    @Override
    public void shoot() {
        playingShootAnimation = true;
        WizardOrb newOrb = new WizardOrb(
                (int) location.centerX(),
                (int) location.top,
                context,
                focusedTarget,
                screenSize, wizardTowerData.getDamage());
        orbs.add(newOrb);
    }


    boolean hasTarget = false;

    private void updateTarget() {
        Log.v("testing", "size: " + targets.size());

        for (EnemyBase enemy : targets) {
            focusedTarget = enemy;

            if (focusedTarget != null && focusedTarget.getHitbox().intersect(getRangeBox())) {
                hasTarget = true;
                return;
            } else if (focusedTarget != null && focusedTarget.getDistanceMoved() < enemy.getDistanceMoved() && enemy.getHitbox().intersect(getRangeBox())) {
                focusedTarget = enemy;
                hasTarget = true;
                return;
            } else if (focusedTarget.shouldRemove()) {
                hasTarget = false;
                focusedTarget = null;
            } else {
                hasTarget = false;
                focusedTarget = null;
            }
        }

        if (focusedTarget != null && !focusedTarget.getHitbox().intersect(getRangeBox())) {
            focusedTarget = null;
            hasTarget = false;
        }
    }

    public void updateTargets(ArrayList<EnemyBase> targets) {
        this.targets = targets;
    }

    long ticks = 0;

    @Override
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

    @Override
    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.left - (wizardTowerData.getRange() * 2),
                (int) location.top - (wizardTowerData.getRange() * 2),
                (int) (location.left + location.width() + (wizardTowerData.getRange() * 2)),
                (int) (location.top + location.height() + (wizardTowerData.getRange() * 2)));

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

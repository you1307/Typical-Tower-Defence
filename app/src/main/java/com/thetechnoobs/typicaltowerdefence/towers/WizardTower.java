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
    ArrayList<WizardOrb> orbs = new ArrayList<>();
    RectF rangBox;

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
        setRangBox();
        settupRangeHitbox();
    }

    public void setRangBox(){
        rangBox = new RectF(
                (int) location.centerX() - (wizardTowerData.getRange()),
                (int) location.centerY() - (wizardTowerData.getRange()),
                (int) (location.centerX() + (wizardTowerData.getRange())),
                (int) (location.centerY() + (wizardTowerData.getRange())));
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
        super.update();

        focusedTarget = updateTargetFirstInLine(wizardTowerData.getRange());

        if (hasTarget) {
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
        if(focusedTarget == null){
            return;
        }

        playingShootAnimation = true;
        WizardOrb newOrb = new WizardOrb(
                (int) location.centerX(),
                (int) location.top,
                context,
                focusedTarget,
                screenSize, wizardTowerData.getDamage());
        orbs.add(newOrb);
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
        return rangBox;
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

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
import com.thetechnoobs.typicaltowerdefence.enemys.EasySlowEnemy;
import com.thetechnoobs.typicaltowerdefence.projectials.WizardOrb;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.WizardTowerData;

import java.util.ArrayList;

public class WizardTower {

    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    Context context;
    boolean shooting = false;
    Paint testPaint = new Paint();
    WizardTowerData wizardTowerData;
    ArrayList<EasySlowEnemy> targets = new ArrayList<>();
    ArrayList<WizardOrb> orbs = new ArrayList<>();
    EasySlowEnemy focusedTarget;
    int[] screenSize;
    int wizardSpriteWidth;
    int wizardSpriteHeight;
    int wizardSpriteFrameLocX = 1;
    int wizardSpriteFrameLocY = 4;
    private int ticks = 0;
    private boolean playingShootAnimation = false;

    public WizardTower(Context context, RectF location, WizardTowerData wizardTowerData, int[] screenSize) {
        resources = context.getResources();
        this.context = context;
        this.screenSize = screenSize;
        this.location = location;
        this.wizardTowerData = wizardTowerData;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    public void draw(Canvas canvas) {
        int x = wizardSpriteWidth * wizardSpriteFrameLocX - wizardSpriteWidth;
        int y = wizardSpriteHeight * wizardSpriteFrameLocY - wizardSpriteHeight;

        frame = new Rect(x, y, x + wizardSpriteWidth, y + wizardSpriteHeight);

        if (playingShootAnimation) {
            shootAnimation();
        }

        for (WizardOrb orb : orbs) {
            orb.draw(canvas);
        }

        drawRangBox(canvas);

        canvas.drawBitmap(towerBitmap, frame, location, null);
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

    private void shoot() {
        playingShootAnimation = true;
        WizardOrb newOrb = new WizardOrb(
                (int) location.centerX(),
                (int) location.centerY(),
                context,
                focusedTarget,
                screenSize);
        orbs.add(newOrb);
    }


    boolean hasTarget = false;
    EasySlowEnemy lastCheck = null;

    private void updateTarget() {
        Log.v("testing", "size: " + targets.size());

        for (EasySlowEnemy enemy : targets) {
            if (lastCheck == null) {
                lastCheck = enemy;
            }

            if (enemy.getHitbox().intersect(getRangeBox()) && !enemy.shouldRemove()) {
                if (enemy.getDistanceMoved() >= lastCheck.getDistanceMoved()) {
                    focusedTarget = enemy;
                    hasTarget = true;
                } else if (enemy.getDistanceMoved() < lastCheck.getDistanceMoved() && lastCheck.getHitbox().intersect(getRangeBox())) {
                    focusedTarget = lastCheck;
                    hasTarget = true;
                } else if (enemy.getHitbox().intersect(getRangeBox())) {
                    focusedTarget = enemy;
                    hasTarget = true;
                } else {
                    hasTarget = false;
                }
            }

            lastCheck = enemy;
        }

        if (focusedTarget != null && !focusedTarget.getHitbox().intersect(getRangeBox())) {
            focusedTarget = null;
            hasTarget = false;
        }
    }

    public void updateTargets(ArrayList<EasySlowEnemy> targets) {
        this.targets = targets;
    }

    public void shootAnimation() {
        ticks++;

        if (ticks % 4 == 1) {
            wizardSpriteFrameLocX++;

            if (wizardSpriteFrameLocX == 8) {
                wizardSpriteFrameLocX = 1;
                playingShootAnimation = false;
                ticks = 0;
            }
        }
    }

    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.left - ((wizardTowerData.getRange() / 2) * 70),
                (int) location.top - ((wizardTowerData.getRange() / 2) * 70),
                (int) (location.left + location.width() + ((wizardTowerData.getRange() / 2) * 70)),
                (int) (location.top + location.height() + ((wizardTowerData.getRange() / 2) * 70)));


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

        wizardSpriteWidth = towerBitmap.getWidth() / 8;
        wizardSpriteHeight = towerBitmap.getHeight() / 4;
    }
}

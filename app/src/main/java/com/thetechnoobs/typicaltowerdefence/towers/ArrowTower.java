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
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.projectials.Arrow;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;

import java.util.ArrayList;

public class ArrowTower {

    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    Context context;
    ArrayList<EnemyBase> targets = new ArrayList<>();
    EnemyBase focusedTarget;
    int arrowSpriteWidth;
    int arrowSpriteHeight;
    int arrowSpriteFrameLocX = 1;
    int arrowSpriteFrameLocY = 3;
    int[] screenSize;
    ArrowTowerData arrowTowerData;
    Paint testPaint = new Paint();
    private boolean playingShootAnimation = false;
    ArrayList<Arrow> arrows = new ArrayList<>();

    public ArrowTower(Context context, RectF location, ArrowTowerData arrowTowerData, int[] screenSize) {
        resources = context.getResources();
        this.context = context;
        this.screenSize = screenSize;
        this.location = location;
        this.arrowTowerData = arrowTowerData;

        testPaint.setColor(Color.GREEN);
        testPaint.setAlpha(60);

        setupBitmap();
        settupRangeHitbox();
    }

    public void draw(Canvas canvas) {
        int x = arrowSpriteWidth * arrowSpriteFrameLocX - arrowSpriteWidth;
        int y = arrowSpriteHeight * arrowSpriteFrameLocY - arrowSpriteHeight;

        frame = new Rect(x, y, x + arrowSpriteWidth, y + arrowSpriteHeight);

        if (playingShootAnimation) {
            shootAnimation();
        }

        for (Arrow arrow : arrows) {
            arrow.draw(canvas);
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

    boolean hasTarget = false;
    private void updateTarget() {
        Log.v("testing", "size: " + targets.size());

        for (EnemyBase enemy : targets) {
            focusedTarget = enemy;

            if(focusedTarget != null && focusedTarget.getHitbox().intersect(getRangeBox())){
                hasTarget = true;
                return;
            }else if(focusedTarget != null && focusedTarget.getDistanceMoved() < enemy.getDistanceMoved() && enemy.getHitbox().intersect(getRangeBox())){
                focusedTarget = enemy;
                hasTarget = true;
                return;
            }else if (focusedTarget.shouldRemove()){
                hasTarget = false;
                focusedTarget = null;
            }else{
                hasTarget = false;
                focusedTarget = null;
            }
        }

        if (focusedTarget != null && !focusedTarget.getHitbox().intersect(getRangeBox())) {
            focusedTarget = null;
            hasTarget = false;
        }
    }

    public RectF getRangeBox() {
        RectF tempR = new RectF(
                (int) location.left - ((arrowTowerData.getRange() / 2) * 70),
                (int) location.top - ((arrowTowerData.getRange() / 2) * 70),
                (int) (location.left + location.width() + ((arrowTowerData.getRange() / 2) * 70)),
                (int) (location.top + location.height() + ((arrowTowerData.getRange() / 2) * 70)));

        RectF bounds = new RectF();
        //bounds.round(tempR);


        return tempR;
    }

    private void shoot() {
        playingShootAnimation = true;
        Arrow newArrow = new Arrow(
                (int) location.centerX(),
                (int) location.centerY(),
                context,
                focusedTarget,
                screenSize);
        arrows.add(newArrow);
    }

    long ticks = 0;

    public void shootAnimation() {
        ticks++;

        if (ticks % 4 == 1) {
            arrowSpriteFrameLocX++;

            if (arrowSpriteFrameLocX == 8) {
                arrowSpriteFrameLocX = 1;
                playingShootAnimation = false;
                ticks = 0;
            }
        }
    }

    RectF rangeHitbox;

    private void settupRangeHitbox() {
        Rect temprec = new Rect(400, 400, 450, 450);
        rangeHitbox = new RectF();
        rangeHitbox.round(temprec);
    }

    private void setupBitmap() {
        towerBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_sprites);

        arrowSpriteWidth = towerBitmap.getWidth() / 8;
        arrowSpriteHeight = towerBitmap.getHeight() / 4;
    }
}

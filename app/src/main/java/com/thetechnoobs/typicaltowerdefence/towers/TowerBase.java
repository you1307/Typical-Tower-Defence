package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.projectials.Arrow;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;

import java.util.ArrayList;

public abstract class TowerBase {
    Bitmap towerBitmap;
    Resources resources;
    RectF location;
    Rect frame;
    Context context;
    ArrayList<EnemyBase> targets = new ArrayList<>();
    EnemyBase focusedTarget;
    int SpriteWidth;
    int SpriteHeight;
    int SpriteFrameLocX = 1;
    int SpriteFrameLocY = 3;
    int[] screenSize;
    Paint testPaint = new Paint();
    boolean playingShootAnimation = false;

    public void draw(Canvas canvas){
        int x = SpriteWidth * SpriteFrameLocX - SpriteWidth;
        int y = SpriteHeight * SpriteFrameLocY - SpriteHeight;

        frame = new Rect(x, y, x + SpriteWidth, y + SpriteHeight);

        if (playingShootAnimation) {
            shootAnimation();
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

    public abstract RectF getRangeBox();
    protected abstract void shoot();
    protected abstract void shootAnimation();
}

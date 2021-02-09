package com.thetechnoobs.typicaltowerdefence.projectials;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;

public class Arrow extends ProjectileBase{


    public Arrow(int x, int y, Context context, EnemyBase target, int[] screenSize, int damage) {
        curX = x;
        curY = y;
        this.screenSize = screenSize;
        this.target = target;
        this.context = context;
        this.damage = damage;
        this.speed = Tools.convertDpToPixel(15f);

        projectileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow);
        calculateVolocity();
        orientateArrow();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(projectileBitmap, getCurX(), getCurY(), null);
    }

    public void update() {
        setCurX((int) (getCurX() + speed * xVelocity));
        setCurY((int) (getCurY() + speed * yVelocity));

        checkCollision();
    }

    public void checkCollision() {
        if (getHitbox().intersect(target.getHitbox())) {
            target.addDamage(damage);
            removeMe = true;
        }
    }

    public RectF getHitbox() {
        return new RectF(
                getCurX(),
                getCurY(),
                getCurX() + projectileBitmap.getWidth(),
                getCurY() + projectileBitmap.getHeight());
    }

    public boolean shouldRemove() {
        if (outOfBounds()) {
            return true;
        }

        if (removeMe) {
            return true;
        } else {
            return false;
        }
    }

    public boolean outOfBounds() {
        if (getCurX() < 0 || getCurX() > screenSize[0] || getCurY() < 0 || getCurY() > screenSize[1]) {
            return true;
        } else {
            return false;
        }
    }

    public void setCurX(int x) {
        curX = x;
    }

    public void setCurY(int y) {
        curY = y;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    private void orientateArrow() {
        double temp = Math.atan2(yVelocity, xVelocity);
        projectileBitmap = rotateBitmap(projectileBitmap, (float) (temp * 62));
    }

    private Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);

        return rotatedBitmap;
    }

    private void calculateVolocity() {
        float totalAllowedMovment = 1.0f;
        float xDistanceFromTarget = Math.abs(target.getCurX() - getCurX());
        float yDistanceFromTarget = Math.abs(target.getCurY() - getCurY());
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovment = xDistanceFromTarget / totalDistanceFromTarget;

        xVelocity = xPercentOfMovment;
        yVelocity = totalAllowedMovment - xPercentOfMovment;

        if (target.getCurX() < getCurX()) {
            xVelocity *= -1;
        }

        if (target.getCurY() < getCurY()) {
            yVelocity *= -1;
        }
    }
}

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
import com.thetechnoobs.typicaltowerdefence.enemys.EasySlowEnemy;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;

public class WizardOrb {
    Bitmap orb;
    int curX, curY;
    float xVelocity, yVelocity;
    Context context;
    EnemyBase target;
    int[] screenSize;
    int speed = 8;
    private boolean removeMe = false;
    Paint testPaint = new Paint();


    public WizardOrb(int x, int y, Context context, EnemyBase target, int[] screenSize) {
        curX = x;
        curY = y;
        this.screenSize = screenSize;
        this.target = target;
        this.context = context;

        orb = BitmapFactory.decodeResource(context.getResources(), R.drawable.orb);
        orb = Bitmap.createScaledBitmap(
                orb,
                (int) Tools.convertDpToPixel(10),
                (int) Tools.convertDpToPixel(20),
                false);

        calculateVolocity();
        orientateOrb();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(orb, getCurX(), getCurY(), null);
    }
    double ticks = 0;
    public void update() {
        calculateVolocity();
        //orientateOrb();

        setCurX((int) (getCurX() + speed * xVelocity));
        setCurY((int) (getCurY() + speed * yVelocity));

        checkCollision();
    }

    public void checkCollision() {
        if (getHitbox().intersect(target.getHitbox())) {
            target.addTimeHit();
            removeMe = true;
        }
    }

    public RectF getHitbox() {
        return new RectF(
                getCurX(),
                getCurY(),
                getCurX() + orb.getWidth(),
                getCurY() + orb.getHeight());
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

    private void orientateOrb() {
        double temp = Math.atan2(yVelocity, xVelocity);
        orb = rotateBitmap(orb, (float) (temp));
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
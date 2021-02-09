package com.thetechnoobs.typicaltowerdefence.projectials;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;

public class ProjectileBase {
    Bitmap projectileBitmap;
    int curX, curY;
    float xVelocity, yVelocity;
    Context context;
    EnemyBase target;
    int damage = 1;
    int[] screenSize;
    float speed = Tools.convertDpToPixel(20f);
    public boolean removeMe = false;
    Paint testPaint = new Paint();
}

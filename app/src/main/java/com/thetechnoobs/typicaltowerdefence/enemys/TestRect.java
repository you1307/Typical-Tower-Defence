package com.thetechnoobs.typicaltowerdefence.enemys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TestRect {
    int x, y;
    int[] screenSize;
    Paint debugPaint = new Paint();

    public TestRect(int x, int y, int[] screenSize){
        this.x = x;
        this.y = y;
        this.screenSize = screenSize;

        debugPaint.setColor(Color.RED);
    }

    public void update(){
        y -= 5;

        if(y<0){
            y=screenSize[1]-100;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawRect(getHitbox(), debugPaint);
    }

    public RectF getHitbox(){
        return new RectF(x, y, x+40, y+40);
    }
}

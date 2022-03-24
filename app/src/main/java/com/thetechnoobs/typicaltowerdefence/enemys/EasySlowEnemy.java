package com.thetechnoobs.typicaltowerdefence.enemys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.ArrayList;

public class EasySlowEnemy extends EnemyBase {

    double distanceMoved = 0;
    Bitmap walkingUpSpriteSheet, walkingLeftSpriteSheet, walkingRightSpriteSheet, walkingDownSpriteSheet;
    Bitmap curDirectionSpriteSheet;
    int spriteWidth, spriteHeight;
    Context context;

    public EasySlowEnemy(int x, int y, int[] screenSize, ArrayList<RectF> mapPath, Context context) {
        setCurX(x);
        setCurY(y);
        this.mapPath = mapPath;
        this.screenSize = screenSize;
        this.context = context;
        this.resources = context.getResources();
        this.MAX_HEATH = 15;
        this.curHeath = MAX_HEATH;

        this.speed = Tools.convertDpToPixel(1f);
        debugPaint.setTextSize(Tools.convertDpToPixel(20));
        loadDirectionSpriteSheets();
    }

    private void loadDirectionSpriteSheets() {
        walkingUpSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.leather_warrior_walk_back_sheet);
        walkingRightSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.leather_walking_right_spritesheet);
        walkingLeftSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.leather_walking_left_spritesheet);

        spriteHeight = walkingUpSpriteSheet.getHeight();
        spriteWidth = walkingUpSpriteSheet.getWidth() / 30;
    }

    @Override
    public void update() {
        super.update();

        if (shouldRemove()) {
            removeMe = true;
        }
    }

    @Override
    public void updateSpriteFrame() {
        switch (direction) {
            case 2:
                curDirectionSpriteSheet = walkingUpSpriteSheet;
                break;
            case 1:
                curDirectionSpriteSheet = walkingLeftSpriteSheet;
                break;
            case 3:
                curDirectionSpriteSheet = walkingRightSpriteSheet;
                break;
            case 4:
                curDirectionSpriteSheet = walkingUpSpriteSheet;
                break;
        }


        spriteHeight = curDirectionSpriteSheet.getHeight();
        spriteWidth = curDirectionSpriteSheet.getWidth() / 30;


        if (curSpriteFrame >= 30) {
            curSpriteFrame = 1;
        }

        curSpriteFrame++;
    }

    private Rect getFrame() {
        int x = spriteWidth * curSpriteFrame - spriteWidth;
        int y = 1;

        return new Rect(x, y, x + spriteWidth, y + spriteHeight);
    }

    public int getDeathReward(){
        return 5;
    }

    public int getHeath(){
        return curHeath;
    }


    public double getDistanceMoved() {
        return distanceMoved;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(curDirectionSpriteSheet != null){
            canvas.drawBitmap(curDirectionSpriteSheet, getFrame(), getHitbox(), null);
        }

        //canvas.drawRect(getHitbox(), debugPaint);
    }

    RectF hitBox = new RectF(getCurX(), getCurY(), getCurX() + (float) Tools.convertDpToPixel(30), getCurY() + (float) Tools.convertDpToPixel(30));
    public RectF getHitbox() {
        hitBox.left = getCurX();
        hitBox.top = getCurY();
        hitBox.right = getCurX() + (float) Tools.convertDpToPixel(30);
        hitBox.bottom = getCurY() + (float) Tools.convertDpToPixel(30);
        return hitBox;
    }
}



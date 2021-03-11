package com.thetechnoobs.typicaltowerdefence.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.thetechnoobs.typicaltowerdefence.GeneralSettings;
import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.UserData;

public class CoinHeader {
    Bitmap moneyBar;
    Resources resources;
    Context context;
    Paint dataTextPaint = new Paint();
    int[] headerLoc;
    UserData userData;

    public CoinHeader(Resources resources, Context context, UserData userData){
        this.resources = resources;
        this.context = context;
        this.userData = userData;

        headerLoc = GeneralSettings.getCoinHeaderLocation(context);

        dataTextPaint.setColor(resources.getColor(R.color.coinGold, null));
        dataTextPaint.setTextSize(Tools.convertDpToPixel(20));
        dataTextPaint.setStrokeWidth(Tools.convertDpToPixel(15));

        iniBitmap();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(moneyBar, headerLoc[0], headerLoc[1], null);

        canvas.drawText(String.valueOf(userData.getUserCoins()),
                headerLoc[0] + (int) Tools.convertDpToPixel(35),
                headerLoc[1] + (int) Tools.convertDpToPixel(25),
                dataTextPaint);

    }

    private void iniBitmap() {
        moneyBar = BitmapFactory.decodeResource(resources, R.drawable.coin_header);
        moneyBar = Bitmap.createScaledBitmap(moneyBar,
                (int) Tools.convertDpToPixel(110),
                (int) Tools.convertDpToPixel(35),
                false);
    }
}

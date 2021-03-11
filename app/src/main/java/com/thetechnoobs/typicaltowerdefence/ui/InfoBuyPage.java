package com.thetechnoobs.typicaltowerdefence.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.GeneralSettings;
import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.CannonTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.TroopsTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.WizardTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.DefaultValues;

public class InfoBuyPage {
    Bitmap infoPageBitmap, buyBtnBitmap, buyBtnBitmapDisabled, upgradeBtnBitmap, upgradeBtnDisabled;
    Resources resources;
    Paint textPaint = new Paint();
    int loadedTower; //1 = arrow, 2 = cannon, 3 = troops, 4 = wizard
    boolean showMe = false;
    float fireRateTXT;
    int damageTXT;
    float price;
    double rangeTXT;
    int[] infoPageLocation;//{x, y};
    RectF buyBTN, upgradeBTN;

    public InfoBuyPage(Context context) {
        this.resources = context.getResources();

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(Tools.convertDpToPixel(15));
        textPaint.setStrokeWidth(Tools.convertDpToPixel(20));

        infoPageLocation = GeneralSettings.getTowerInfoPageLocation(context);

        iniBitmaps();
        iniButtonPos();
    }

    public int getLoadedTower(){
        return loadedTower;
    }

    private void iniButtonPos() {
        buyBTN = new RectF(
                infoPageLocation[0] + Tools.convertDpToPixel(63),
                infoPageLocation[1] + Tools.convertDpToPixel(200),
                infoPageLocation[0] + Tools.convertDpToPixel(140),
                infoPageLocation[1] + Tools.convertDpToPixel(235));

        upgradeBTN = new RectF(
                infoPageLocation[0] + Tools.convertDpToPixel(110),
                infoPageLocation[1] + Tools.convertDpToPixel(200),
                infoPageLocation[0] + Tools.convertDpToPixel(190),
                infoPageLocation[1] + Tools.convertDpToPixel(240));
    }

    private void iniBitmaps() {
        infoPageBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_info_page);
        infoPageBitmap = Bitmap.createScaledBitmap(infoPageBitmap,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);

        buyBtnBitmap = BitmapFactory.decodeResource(resources, R.drawable.buy_button);
        buyBtnBitmap = Bitmap.createScaledBitmap(buyBtnBitmap,
                (int) Tools.convertDpToPixel(80),
                (int) Tools.convertDpToPixel(35),
                false);

        buyBtnBitmapDisabled = BitmapFactory.decodeResource(resources, R.drawable.buy_button_disable);
        buyBtnBitmapDisabled = Bitmap.createScaledBitmap(buyBtnBitmapDisabled,
                (int) Tools.convertDpToPixel(80),
                (int) Tools.convertDpToPixel(35),
                false);

        upgradeBtnBitmap = BitmapFactory.decodeResource(resources, R.drawable.upgrade_button);
        upgradeBtnBitmap = Bitmap.createScaledBitmap(upgradeBtnBitmap,
                (int) Tools.convertDpToPixel(80),
                (int) Tools.convertDpToPixel(35),
                false);

        upgradeBtnDisabled = BitmapFactory.decodeResource(resources, R.drawable.upgrade_button_disabled);
        upgradeBtnDisabled = Bitmap.createScaledBitmap(upgradeBtnDisabled,
                (int) Tools.convertDpToPixel(80),
                (int) Tools.convertDpToPixel(35),
                false);
    }

    public void setShowMe(Boolean show){
        showMe = show;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(infoPageBitmap, infoPageLocation[0], infoPageLocation[1], null);

        canvas.drawRect(buyBTN, textPaint);

        canvas.drawBitmap(buyBtnBitmap,
                infoPageLocation[0] + Tools.convertDpToPixel(60),
                infoPageLocation[1] + Tools.convertDpToPixel(200),
                null);

//        canvas.drawBitmap(upgradeBtnBitmap,
//                infoPageLocation[0] + Tools.convertDpToPixel(110),
//                infoPageLocation[1] + Tools.convertDpToPixel(200),
//                null);

        drawData(canvas);
    }

    public boolean shouldShow(){
        return showMe;
    }

    public boolean buyButtonPushed(RectF touchPoint){
        if(touchPoint.intersect(buyBTN)){
            return true;
        }else{
            return false;
        }
    }

    private void drawData(Canvas canvas) {
        canvas.drawText(String.valueOf(price),
                infoPageLocation[0] + Tools.convertDpToPixel(10),
                infoPageLocation[1] + Tools.convertDpToPixel(30),
                textPaint);

        canvas.drawText("Damage: " + damageTXT,
                infoPageLocation[0] + Tools.convertDpToPixel(10),
                infoPageLocation[1] + Tools.convertDpToPixel(60),
                textPaint);

        canvas.drawText("Fire rate: " + fireRateTXT,
                infoPageLocation[0] + Tools.convertDpToPixel(10),
                infoPageLocation[1] + Tools.convertDpToPixel(80),
                textPaint);

        canvas.drawText("Range: " + rangeTXT,
                infoPageLocation[0] + Tools.convertDpToPixel(10),
                infoPageLocation[1] + Tools.convertDpToPixel(100),
                textPaint);

        if (loadedTower == 3) {
            canvas.drawText("Troop size: " + troopSizeTXT,
                    infoPageLocation[0] + Tools.convertDpToPixel(10),
                    infoPageLocation[1] + Tools.convertDpToPixel(120),
                    textPaint);
        }
    }

    public void loadArrowTowerData() {
        loadedTower = 1;
        rangeTXT = DefaultValues.arrowRange;
        damageTXT = DefaultValues.arrowDamage;
        fireRateTXT = DefaultValues.arrowFireRate;
        price = DefaultValues.arrowPrice;
    }

    public void loadCannonTowerData() {
        loadedTower = 2;
        rangeTXT = DefaultValues.cannonRange;
        damageTXT = DefaultValues.cannonDamage;
        fireRateTXT = DefaultValues.cannonFireRate;
        price = DefaultValues.cannonPrice;
    }

    int troopSizeTXT;
    public void loadTroopsTowerData() {
        loadedTower = 3;
        troopSizeTXT = DefaultValues.troopsSize;
        rangeTXT = DefaultValues.troopsRange;
        damageTXT = DefaultValues.troopsDamage;
        fireRateTXT = DefaultValues.troopsFireRate;
        price = DefaultValues.troopsPrice;
    }

    public void loadWizardTowerData() {
        loadedTower = 4;
        rangeTXT = DefaultValues.wizardRange;
        damageTXT = DefaultValues.wizardDamage;
        fireRateTXT = DefaultValues.wizardFireRate;
        price = DefaultValues.wizardPrice;
    }
}

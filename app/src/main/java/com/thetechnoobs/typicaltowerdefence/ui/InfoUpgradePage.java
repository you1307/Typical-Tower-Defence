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
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.CannonTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.TroopsTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.WizardTowerData;

public class InfoUpgradePage {
    Bitmap infoPageBitmap, upgradeBtnBitmap, upgradeBtnDisabled;
    Resources resources;
    Paint textPaint = new Paint();
    int loadedTower; //1 = arrow, 2 = cannon, 3 = troops, 4 = wizard
    boolean showMe = false;
    float fireRateTXT;
    int damageTXT, rangeTXT, randomNum;
    int[] infoPageLocation;//{x, y};
    RectF upgradeBTN;

    public InfoUpgradePage(Context context) {
        this.resources = context.getResources();

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(Tools.convertDpToPixel(15));
        textPaint.setStrokeWidth(Tools.convertDpToPixel(20));

        infoPageLocation = GeneralSettings.getTowerInfoPageLocation(context);

        iniBitmaps();
        iniButtonPos();
    }

    private void iniButtonPos() {
        upgradeBTN = new RectF(
                infoPageLocation[0] + Tools.convertDpToPixel(63),
                infoPageLocation[1] + Tools.convertDpToPixel(200),
                infoPageLocation[0] + Tools.convertDpToPixel(140),
                infoPageLocation[1] + Tools.convertDpToPixel(235));
    }

    private void iniBitmaps() {
        infoPageBitmap = BitmapFactory.decodeResource(resources, R.drawable.tower_info_page);
        infoPageBitmap = Bitmap.createScaledBitmap(infoPageBitmap,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
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

        canvas.drawRect(upgradeBTN, textPaint);

        canvas.drawBitmap(upgradeBtnBitmap,
                infoPageLocation[0] + Tools.convertDpToPixel(60),
                infoPageLocation[1] + Tools.convertDpToPixel(200),
                null);
        drawData(canvas);
    }

    public boolean shouldShow(){
        return showMe;
    }

    public boolean upgradeButtonPushed(RectF touchPoint){
        if(touchPoint.intersect(upgradeBTN)){
            return true;
        }else{
            return false;
        }
    }

    private void drawData(Canvas canvas) {
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

        canvas.drawText("Tower Name: "+randomNum,
                infoPageLocation[0] + Tools.convertDpToPixel(10),
                infoPageLocation[1] + Tools.convertDpToPixel(140),
                textPaint);

        if (loadedTower == 3) {
            canvas.drawText("Troop size: " + troopSizeTXT,
                    infoPageLocation[0] + Tools.convertDpToPixel(10),
                    infoPageLocation[1] + Tools.convertDpToPixel(120),
                    textPaint);
        }
    }

    public void loadArrowTowerData(ArrowTowerData arrowTowerData) {
        loadedTower = 1;
        rangeTXT = arrowTowerData.getRange();
        damageTXT = arrowTowerData.getDamage();
        fireRateTXT = arrowTowerData.getFireRate();
        randomNum = arrowTowerData.getRandomNum();
    }

    public void loadCannonTowerData(CannonTowerData cannonTowerData) {
        loadedTower = 2;
        rangeTXT = cannonTowerData.getRange();
        damageTXT = cannonTowerData.getDamage();
        fireRateTXT = cannonTowerData.getFireRate();
        randomNum = cannonTowerData.getRandomNum();
    }

    int troopSizeTXT;
    public void loadTroopsTowerData(TroopsTowerData troopsTowerData) {
        loadedTower = 3;
        troopSizeTXT = troopsTowerData.getTroopSize();
        rangeTXT = troopsTowerData.getRange();
        damageTXT = troopsTowerData.getDamage();
        fireRateTXT = troopsTowerData.getFireRate();
        randomNum = troopsTowerData.getRandomNum();
    }

    public void loadWizardTowerData(WizardTowerData wizardTowerData) {
        loadedTower = 4;
        rangeTXT = wizardTowerData.getRange();
        damageTXT = wizardTowerData.getDamage();
        fireRateTXT = wizardTowerData.getFireRate();
        randomNum = wizardTowerData.getRandomNum();
    }

    public void setData(PlotHandler plotHandler) {
        switch (plotHandler.getOccupant()){
            case 1:
                loadArrowTowerData(plotHandler.getArrowData());
                break;
            case 2:
                loadCannonTowerData(plotHandler.getCannonData());
                break;
            case 3:
                loadTroopsTowerData(plotHandler.getTroopsData());
                break;
            case 4:
                loadWizardTowerData(plotHandler.getWizardData());
                break;
        }
    }
}

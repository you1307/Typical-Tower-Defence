package com.thetechnoobs.typicaltowerdefence.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.Tools;
import com.thetechnoobs.typicaltowerdefence.towers.PlotHandler;

import java.util.ArrayList;

public class TowerBuySelectWheel {
    Paint buttonMarkerPaint = new Paint();
    Bitmap towerSelectWheel, towerSelectWheelNone, towerSelectWheelArrow, towerSelectWheelWizard, towerSelectWheelCannon, towerSelectWheelTroops;
    Resources resources;
    InfoBuyPage infoBuyPage;
    int[] location = {0, 0};
    int curTowerSelection = -1;
    Boolean showMe = false;
    ArrayList<RectF> towerButtons = new ArrayList<>();

    public TowerBuySelectWheel(Resources resources, InfoBuyPage towerBuyInfo) {
        this.resources = resources;
        this.infoBuyPage = towerBuyInfo;
        buttonMarkerPaint.setColor(Color.RED);
        buttonMarkerPaint.setAlpha(50);

        setImgRef();
        towerSelectWheel = towerSelectWheelNone;
    }

    private void setImgRef() {
        towerSelectWheelNone = BitmapFactory.decodeResource(resources, R.drawable.tower_select_wheel_none_select);
        towerSelectWheelArrow = BitmapFactory.decodeResource(resources, R.drawable.tower_select_wheel_arrow_tower_select);
        towerSelectWheelWizard = BitmapFactory.decodeResource(resources, R.drawable.tower_select_wheel_wizard_tower_select);
        towerSelectWheelCannon = BitmapFactory.decodeResource(resources, R.drawable.tower_select_wheel_cannon_tower_select);
        towerSelectWheelTroops = BitmapFactory.decodeResource(resources, R.drawable.tower_select_wheel_troops_tower_select);


        towerSelectWheelNone = Bitmap.createScaledBitmap(towerSelectWheelNone,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);

        towerSelectWheelArrow = Bitmap.createScaledBitmap(towerSelectWheelArrow,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);

        towerSelectWheelWizard = Bitmap.createScaledBitmap(towerSelectWheelWizard,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);

        towerSelectWheelCannon = Bitmap.createScaledBitmap(towerSelectWheelCannon,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);

        towerSelectWheelTroops = Bitmap.createScaledBitmap(towerSelectWheelTroops,
                (int) Tools.convertDpToPixel(200),
                (int) Tools.convertDpToPixel(200),
                false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(towerSelectWheel,
                location[0] - (towerSelectWheel.getWidth() / 2),
                location[1] - (towerSelectWheel.getWidth() / 2),
                null);


//        for (RectF towerButton : towerButtons) {
//            canvas.drawRect(towerButton, buttonMarkerPaint);
//        }
    }

    public void setLocation(RectF plot, InfoBuyPage infoBuyPage) {
        location[0] = (int) (plot.left + plot.width() / 2);
        location[1] = (int) (plot.top + plot.height() / 2);

        if (towerButtons.size() > 0) {
            towerButtons.clear();
        }

        RectF arrowButton = new RectF(
                location[0] - Tools.convertDpToPixel(95),
                location[1] - Tools.convertDpToPixel(40),
                location[0] - Tools.convertDpToPixel(20),
                location[1] + Tools.convertDpToPixel(27));

        RectF troopsButton = new RectF(
                location[0] + Tools.convertDpToPixel(20),
                location[1] - Tools.convertDpToPixel(40),
                location[0] + Tools.convertDpToPixel(93),
                location[1] + Tools.convertDpToPixel(27));

        RectF wizardButton = new RectF(
                location[0] - Tools.convertDpToPixel(40),
                location[1] + Tools.convertDpToPixel(30),
                location[0] + Tools.convertDpToPixel(36),
                location[1] + Tools.convertDpToPixel(100));

        RectF cannonButton = new RectF(
                location[0] - Tools.convertDpToPixel(36),
                location[1] - Tools.convertDpToPixel(100),
                location[0] + Tools.convertDpToPixel(36),
                location[1] - Tools.convertDpToPixel(30));


        towerButtons.add(arrowButton);
        towerButtons.add(troopsButton);
        towerButtons.add(wizardButton);
        towerButtons.add(cannonButton);
    }

    public int checkPress(RectF touchPoint) {
        if (touchPoint.intersect(towerButtons.get(0))) {
            arrowTowerSelected();
            return 1;
        } else if (touchPoint.intersect(towerButtons.get(1))) {
            troopsTowerSelected();
            return 1;
        } else if (touchPoint.intersect(towerButtons.get(2))) {
            wizardTowerSelected();
            return 1;
        } else if (touchPoint.intersect(towerButtons.get(3))) {
            cannonTowerSelected();
            return 1;
        } else{
            setDefaultIMG();
            return 0;
        }
    }

    private void setDefaultIMG() {
        towerSelectWheel = towerSelectWheelNone;
    }

    private void cannonTowerSelected() {
        towerSelectWheel = towerSelectWheelCannon;
        curTowerSelection = 2;
        drawinfoPage(2);
    }

    private void wizardTowerSelected() {
        towerSelectWheel = towerSelectWheelWizard;
        curTowerSelection = 4;
        drawinfoPage(4);
    }

    private void troopsTowerSelected() {
        towerSelectWheel = towerSelectWheelTroops;
        curTowerSelection = 3;
        drawinfoPage(3);
    }

    private void arrowTowerSelected() {
        towerSelectWheel = towerSelectWheelArrow;
        curTowerSelection = 1;
        drawinfoPage(1);
    }

    private void drawinfoPage(int tower) {
        switch (tower) {
            case 1:
                infoBuyPage.setShowMe(true);
                infoBuyPage.loadArrowTowerData();
                break;
            case 2:
                infoBuyPage.setShowMe(true);
                infoBuyPage.loadCannonTowerData();
                break;
            case 3:
                infoBuyPage.setShowMe(true);
                infoBuyPage.loadTroopsTowerData();
                break;
            case 4:
                infoBuyPage.setShowMe(true);
                infoBuyPage.loadWizardTowerData();
                break;
        }
    }

    public void setShowMe(Boolean show){
        if(!show){
            setDefaultIMG();
        }
        showMe = show;
    }

    public boolean shouldShow() {
        return showMe;
    }
}

package com.thetechnoobs.typicaltowerdefence.towers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.thetechnoobs.typicaltowerdefence.R;
import com.thetechnoobs.typicaltowerdefence.enemys.EnemyBase;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.ArrowTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.CannonTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.TroopsTowerData;
import com.thetechnoobs.typicaltowerdefence.towers.towerData.WizardTowerData;

import java.util.ArrayList;

public class PlotHandler {

    private final Context context;
    Bitmap availablePlotBitmap;
    RectF location;
    int arrayPos;
    int[] screenSize;
    boolean available = true;
    Paint debugPaint = new Paint();
    int towerType = 0; //0 = none, 1 = arrow, 2 = cannon, 3 = troops, 4 = wizard

    public PlotHandler(RectF location, Context context, int arrayPos) {
        this.location = location;
        this.arrayPos = arrayPos;
        this.context = context;
        debugPaint.setColor(Color.GREEN);
        debugPaint.setAlpha(70);

        settupBitmap();
    }

    private void settupBitmap() {
        availablePlotBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_tower_plot);
        availablePlotBitmap = Bitmap.createScaledBitmap(availablePlotBitmap,
                (int) location.width(),
                (int) location.height(),
                false);
    }

    public void draw(Canvas canvas) {
        if (isAvailable()) {
            canvas.drawBitmap(availablePlotBitmap, (int) location.left, (int) location.top, null);
            //canvas.drawRect(location, debugPaint);
        }

        if (!isAvailable()) {
            switch (towerType) {
                case 0:
                    break;
                case 1:
                    arrowTower.draw(canvas);
                    break;
                case 2:
                    cannonTower.draw(canvas);
                    break;
                case 3:
                    troopsTower.draw(canvas);
                    break;
                case 4:
                    wizardTower.draw(canvas);
                    break;
            }
        }
    }

    public void update(ArrayList<EnemyBase> targets) {
        if (!isAvailable()) {
            switch (towerType) {
                case 0:
                    break;
                case 1:
                    arrowTower.updateTargets(targets);
                    arrowTower.update();
                    break;
                case 2:
                    cannonTower.updateTargets(targets);
                    cannonTower.update();
                    break;
                case 3:
                    troopsTower.update();
                    break;
                case 4:
                    wizardTower.updateTargets(targets);
                    wizardTower.update();
                    break;
            }
        }
    }

    public RectF getLocation() {
        return location;
    }

    public void setTowerType(int type) {
        switch (type) {
            case 1:
                towerType = 1;
                settupNewArrowTowerOnPlot();
                setUnavailable();
                break;
            case 2:
                towerType = 2;
                settupNewCannonTowerOnPlot();
                setUnavailable();
                break;
            case 3:
                towerType = 3;
                settupNewTroopsTowerOnPlot();
                setUnavailable();
                break;
            case 4:
                towerType = 4;
                settupNewWizardTowerOnPlot();
                setUnavailable();
                break;
        }
    }

    TroopsTower troopsTower;
    TroopsTowerData troopsTowerData;

    private void settupNewTroopsTowerOnPlot() {
        troopsTowerData = new TroopsTowerData(context);
        troopsTowerData.setDefalutValues();
        troopsTower = new TroopsTower(context, location);
    }

    CannonTower cannonTower;
    CannonTowerData cannonTowerData;

    private void settupNewCannonTowerOnPlot() {
        cannonTowerData = new CannonTowerData(context);
        cannonTowerData.setDefalutValues();
        cannonTower = new CannonTower(context, location, cannonTowerData, screenSize);
    }


    ArrowTower arrowTower;
    ArrowTowerData arrowTowerData;

    private void settupNewArrowTowerOnPlot() {
        arrowTowerData = new ArrowTowerData(context);
        arrowTowerData.setDefalutValues();
        arrowTower = new ArrowTower(context, location, arrowTowerData, screenSize);
    }

    WizardTower wizardTower;
    WizardTowerData wizardTowerData;

    private void settupNewWizardTowerOnPlot() {
        wizardTowerData = new WizardTowerData(context);
        wizardTowerData.setDefalutValues();
        wizardTower = new WizardTower(context, location, wizardTowerData, screenSize);
    }

    public int getOccupant() {
        return towerType;
    }

    public void setUnavailable() {
        available = false;
    }

    public boolean isAvailable() {
        return available;
    }

    public Boolean checkTouch(RectF touchPoint) {
        if (touchPoint.intersect(location)) {
            return true;
        } else {
            return false;
        }
    }

    public int getArrayPos() {
        return arrayPos;
    }

    public ArrowTowerData getArrowData() {
        return arrowTowerData;
    }

    public CannonTowerData getCannonData() {
        return cannonTowerData;
    }

    public TroopsTowerData getTroopsData() {
        return troopsTowerData;
    }

    public WizardTowerData getWizardData() {
        return wizardTowerData;
    }

    public void setScreenSize(int[] screenSize) {
        this.screenSize = screenSize;
    }

    public void removeTower() {
        towerType = 0;
        available = true;
    }
}

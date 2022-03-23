package com.thetechnoobs.typicaltowerdefence.towers.towerData;

import android.content.Context;
import android.util.Log;

import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.Random;

public class ArrowTowerData {
    private static final String TAG = "ArrowTowerData";
    Context context;
    int damage, price;
    float range;
    float fireRate;

    public ArrowTowerData(Context context) {
        this.context = context;
    }

    public float getRange() {
        return range;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public float getFireRate() {
        if(fireRate < 0){
            return 1f;
        }else{
            return fireRate;
        }
    }

    public int getRandomNum() {
        return randomNum;
    }


    int randomNum;
    public void setDefalutValues() {
        randomNum = new Random().nextInt(1000);
        range = DefaultValues.arrowRange;
        damage = DefaultValues.arrowDamage;
        fireRate = DefaultValues.arrowFireRate;
        price = DefaultValues.arrowPrice;
    }

    public void upgrade() {
        damage += 1;
        range += Tools.convertDpToPixel(2);
        fireRate -= 50f;
        price += 10;
    }
}

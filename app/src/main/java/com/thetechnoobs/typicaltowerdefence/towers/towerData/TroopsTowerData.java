package com.thetechnoobs.typicaltowerdefence.towers.towerData;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class TroopsTowerData {
    Context context;
    int plotNum;
    int damage, troopSize, price;
    float fireRate, range;

    public TroopsTowerData(Context context) {
        this.context = context;
        this.plotNum = plotNum;
    }

    public float getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getPrice(){
        return price;
    }

    public float getFireRate() {
        return fireRate;
    }

    public int getRandomNum(){
        return randomNum;
    }

    public int getTroopSize(){
        return troopSize;
    }


    int randomNum;
    public void setDefalutValues() {
        randomNum = new Random().nextInt(1000);
        range = DefaultValues.troopsRange;
        damage = DefaultValues.troopsDamage;
        fireRate = DefaultValues.troopsFireRate;
        troopSize = DefaultValues.troopsSize;
        price = DefaultValues.troopsPrice;
    }
}

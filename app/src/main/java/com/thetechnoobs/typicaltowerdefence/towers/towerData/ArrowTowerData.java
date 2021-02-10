package com.thetechnoobs.typicaltowerdefence.towers.towerData;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class ArrowTowerData {
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

    public int getPrice(){
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public float getFireRate() {
        return fireRate;
    }

    public int getRandomNum(){
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
        
    }
}

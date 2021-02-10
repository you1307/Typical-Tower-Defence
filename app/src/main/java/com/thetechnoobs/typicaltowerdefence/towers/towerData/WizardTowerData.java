package com.thetechnoobs.typicaltowerdefence.towers.towerData;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class WizardTowerData {
    Context context;
    int plotNum;
    int damage, price;
    float fireRate, range;

    public WizardTowerData(Context context) {
        this.context = context;
        this.plotNum = plotNum;
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
        range = DefaultValues.wizardRange;
        damage = DefaultValues.wizardDamage;
        fireRate = DefaultValues.wizardFireRate;
        price = DefaultValues.wizardPrice;
    }
}

package com.thetechnoobs.typicaltowerdefence.towers.towerData;

import android.content.Context;

import com.thetechnoobs.typicaltowerdefence.Tools;

import java.util.Random;

public class CannonTowerData {
    Context context;
    int damage;
    float price;
    float fireRate, range;

    public CannonTowerData(Context context) {
        this.context = context;
    }

    public float getRange() {
        if(range < 0){
            return 1f;
        }else{
            return range;
        }
    }

    public float getPrice(){
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
        range = DefaultValues.cannonRange;
        damage = DefaultValues.cannonDamage;
        fireRate = DefaultValues.cannonFireRate;
        price = DefaultValues.cannonPrice;
    }

    public void upgrade() {
        damage += 1;
        range += Tools.convertDpToPixel(2);
        fireRate -= 50f;
        price += 30;
    }
}

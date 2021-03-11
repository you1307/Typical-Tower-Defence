package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    private int coins = 50;

    public void addCoins(int coinsToAdd){
        coins += coinsToAdd;
    }

    public int getUserCoins(){
        return coins;
    }
}

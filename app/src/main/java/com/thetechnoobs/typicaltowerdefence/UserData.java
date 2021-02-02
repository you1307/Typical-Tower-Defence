package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    public static void addCoins(Context context, int coins){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("coins", coins);
        editor.apply();
    }

    public static int getUserCoins(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("coins", 999999);
    }
}

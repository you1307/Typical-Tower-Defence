package com.thetechnoobs.typicaltowerdefence;

import android.content.Context;
import android.content.SharedPreferences;

public class GeneralSettings {
    public static int[] getTowerInfoPageLocation(Context context){
        int[] location = {0,0};

        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        location[0] = sharedPreferences.getInt("towerInfoPageLocX", 300);
        location[1] = sharedPreferences.getInt("towerInfoPageLocY", 300);
        return location;
    }

    public static int[] getCoinHeaderLocation(Context context){
        int[] location = {0,0};

        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        location[0] = sharedPreferences.getInt("coinHeaderLocX", (int) Tools.convertDpToPixel(10));
        location[1] = sharedPreferences.getInt("coinHeaderLocY", (int) Tools.convertDpToPixel(10));
        return location;
    }
}

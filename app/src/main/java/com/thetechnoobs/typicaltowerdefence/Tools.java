package com.thetechnoobs.typicaltowerdefence;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class Tools {
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * (metrics.densityDpi / 160f));
    }
}

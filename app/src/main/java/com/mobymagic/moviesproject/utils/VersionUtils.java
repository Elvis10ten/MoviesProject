package com.mobymagic.moviesproject.utils;

import android.os.Build;

public class VersionUtils {

    /**
     * Checks if the device have exactly Android KitKat
     * @return true if the device is on Android KitKat, false otherwise
     */
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    /**
     * Checks if the device have Android JellyBean and above (higher versions)
     * @return true if the device has Android JellyBean and above, false otherwise
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Checks if the device have Android JellyBean_MR1 and above (higher versions)
     * @return true if the device has Android JellyBean_MR1 and above, false otherwise
     */
    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Checks if the device have Android Nougat and above (higher versions)
     * @return true if the device has Android Nougat and above, false otherwise
     */
    public static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * Checks if the device have Android Lollipop and above (higher versions)
     * @return true if the device has Android Lollipop and above, false otherwise
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Checks if the device have Android Marshmallow and above (higher versions)
     * @return true if the device has Android Marshmallow and above, false otherwise
     */
    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Checks if the device have Android KitKat and above (higher versions)
     * @return true if the device has Android KitKat and above, false otherwise
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}
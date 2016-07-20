package com.eyeem.deviceinfo;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by budius on 07.07.15.
 */
public class DeviceInfo {

   /**
    * Get or create an instance of DeviceInfo with all latest info about this device
    *
    * @param context context to base the device info from
    * @return a fresh (or cached) version of DeviceInfo.
    */
   public static DeviceInfo get(@NonNull Context context) {
      // all the creation/caching/init of DeviceInfo params is delegated to the factory
      return Factory.get(context);
   }

   /**
    * Get or create an instance of DeviceInfo with all latest info about this device
    *
    * @param view view to get context from.
    * @return a fresh (or cached) version of DeviceInfo.
    */
   public static DeviceInfo get(@NonNull View view) {
      return get(view.getContext());
   }

   /**
    * is the device a 7 inch tablet
    */
   public final boolean is7inch;

   /**
    * is the device a 10 inch tablet
    */
   public final boolean is10inch;

   /**
    * is the device in portrait orientation
    */
   public final boolean isPortrait;

   /**
    * is the device in landscape orientation
    */
   public final boolean isLandscape;

   /**
    * is the device a phone
    */
   public final boolean isPhone;

   /**
    * is the device a tablet
    */
   public final boolean isTablet;

   /**
    * is an amazon device TODO: expand it to enum(?) to have expandable options.
    */
   public final boolean isAmazon;

   /**
    * Activity height in pixels.
    */
   public final int heightPixels;

   /**
    * Activity width in pixels
    */
   public final int widthPixels;

   /**
    * Device status bar height
    */
   public final int statusBarHeight;

   /**
    * Device navigation bar height
    */
   public final int navigationBarHeight;

   /**
    * Device diagonal screen in inches.
    */
   public final float diagonalScreenSize;

   /**
    * Device display real size
    */
   private final Point displayRealSize;

   /**
    * Keeping the application for info that can change at any moment
    * E.g. networking info
    */
   private final Application app;

   DeviceInfo(
         boolean is7inch,
         boolean is10inch,
         boolean isPortrait,
         boolean isLandscape,
         boolean isPhone,
         boolean isTablet,
         boolean isAmazon,
         int heightPixels,
         int widthPixels,
         int statusBarHeight,
         int navigationBarHeight,
         float diagonalScreenSize,
         Point displayRealSize,
         Application app
   ) {
      this.is7inch = is7inch;
      this.is10inch = is10inch;
      this.isPortrait = isPortrait;
      this.isLandscape = isLandscape;
      this.isPhone = isPhone;
      this.isTablet = isTablet;
      this.isAmazon = isAmazon;
      this.heightPixels = heightPixels;
      this.widthPixels = widthPixels;
      this.statusBarHeight = statusBarHeight;
      this.navigationBarHeight = navigationBarHeight;
      this.diagonalScreenSize = diagonalScreenSize;
      this.displayRealSize = displayRealSize;
      this.app = app;
   }

   /**
    * Device real size in pixels.
    *
    * @return size of the physical display on the device (e.g. Full HD device is 1920x1080)
    */
   public Point getDisplayRealSize() {
      return new Point(displayRealSize);
   }

   /**
    * Check connection type for wifi
    *
    * @return true, if current connection is WiFi
    */
   public boolean isWifiConnection() {
      try {
         ConnectivityManager manager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo ni = manager.getActiveNetworkInfo();
         return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
      } catch (Throwable t) {
         // https://fabric.io/eyeem/android/apps/com.baseapp.eyeem/issues/577f965affcdc0425064069f
         return false;
      }
   }

}

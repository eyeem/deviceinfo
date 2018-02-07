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

   public enum NavigationBarGravity {
      HORIZONTAL, VERTICAL, NONE
   }

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
    * Same as calling Activity.isInMultiWindowMode()
    */
   public final boolean isInMultiWindowMode;

   /**
    * Same as calling Activity.isInPictureInPictureMode()
    */
   public final boolean isInPictureInPictureMode;

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
    * Activity height in density independent pixels.
    */
   public final int heightDip;

   /**
    * Activity width in density independent pixels
    */
   public final int widthDip;

   /**
    * Device smallest width (in DP).
    * This value matches the `swXXXdp` identifier from resources folder.
    */
   public final int smallestWidthDp;

   /**
    * Device status bar height
    */
   public final int statusBarHeight;

   /**
    * Device navigation bar height
    * Deprecated: use {@link #navigationBarDimensions} or
    * {@link #navigationBarGravity} with {@link #navigationBarSpace}
    */
   @Deprecated public final int navigationBarHeight;

   /**
    * Device navigation bar height or width.
    * If navigationBarDimensions.x != 0, bar is on the left/right side.
    * If navigationBarDimensions.y != 0, bar is on the bottom side.
    */
   public final Point navigationBarDimensions;

   /**
    * Device navigation bar gravity based on device orientation.
    * HORIZONTAL, VERTICAL, NONE
    */
   public final NavigationBarGravity navigationBarGravity;

   /**
    * Space on screen that navigation bar is taking.
    * To be used in combination with {@link #navigationBarGravity}
    */
   public final int navigationBarSpace;

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
         boolean isInMultiWindowMode,
         boolean isInPictureInPictureMode,
         boolean isAmazon,
         int heightPixels,
         int widthPixels,
         int heightDip,
         int widthDip,
         int smallestWidthDp,
         int statusBarHeight,
         Point navigationBarDimensions,
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
      this.isInMultiWindowMode = isInMultiWindowMode;
      this.isInPictureInPictureMode = isInPictureInPictureMode;
      this.isAmazon = isAmazon;
      this.heightPixels = heightPixels;
      this.widthPixels = widthPixels;
      this.heightDip = heightDip;
      this.widthDip = widthDip;
      this.smallestWidthDp = smallestWidthDp;
      this.statusBarHeight = statusBarHeight;
      this.navigationBarDimensions = navigationBarDimensions;
      this.navigationBarGravity = getNavigationBarGravity();
      this.navigationBarSpace = getNavigationBarHeight();
      this.navigationBarHeight = getNavigationBarHeight();
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

   private int getNavigationBarHeight() {
      return navigationBarDimensions.x > navigationBarDimensions.y ?
            navigationBarDimensions.x : navigationBarDimensions.y;
   }

   private NavigationBarGravity getNavigationBarGravity() {
      if (navigationBarDimensions.x == 0 && navigationBarDimensions.y == 0) {
         return NavigationBarGravity.NONE;
      } else if (navigationBarDimensions.x > navigationBarDimensions.y) {
         return NavigationBarGravity.VERTICAL;
      } else {
         return NavigationBarGravity.HORIZONTAL;
      }
   }

}

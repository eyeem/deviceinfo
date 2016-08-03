package com.eyeem.deviceinfo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.WeakHashMap;

/**
 * Created by budius on 13.07.16.
 */
public class Factory {

   private static final WeakHashMap<Context, DeviceInfo> CACHE = new WeakHashMap<>();

   public static DeviceInfo get(Context base) {

      DeviceInfo di;
      Context context = getActivityContext(base);

      // only activity context we can use the cached object
      // for others, we must create a new to ensure data is good
      if (context != null) {
         di = CACHE.get(context);
         if (di != null) {
            return di;
         }
      }

      // if activity context is null, we'll use the application context
      if (context == null) {
         context = base.getApplicationContext();
      }

      // display metrics can be `null` in a bunch of situations,
      // so we'll try every possibility until we find one that works
      DisplayMetrics displayMetrics = getDisplayMetrics(context);
      Resources res = context.getResources();

      boolean is7inch;
      boolean is10inch;
      boolean isPortrait;
      boolean isLandscape;
      boolean isPhone;
      boolean isTablet;
      boolean isAmazon;
      boolean isMultiWindow;
      boolean isPiP;
      int heightPixels;
      int widthPixels;
      int heightDip;
      int widthDip;
      int smallestWidthDp;
      int statusBarHeight;
      int navigationBarHeight;
      float diagonalScreenSize;
      Point displayRealSize;

      is7inch = res.getBoolean(R.bool.is7inch);
      is10inch = res.getBoolean(R.bool.is10inch);
      isPortrait = res.getBoolean(R.bool.isPortrait);

      isLandscape = !isPortrait;
      isTablet = is7inch || is10inch;
      isPhone = !isTablet;

      // from: http://stackoverflow.com/a/19643816
      boolean isAmazonDevice = Build.MANUFACTURER.equalsIgnoreCase("amazon");
      String installerName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
      boolean fromAmazonStore = installerName != null && installerName.equalsIgnoreCase("com.amazon.venezia");

      if (context instanceof Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
         Activity a = (Activity) context;
         isMultiWindow = getMultiWindow(a);
         isPiP = getPip(a);
      } else {
         isMultiWindow = false;
         isPiP = false;
      }

      isAmazon = isAmazonDevice && fromAmazonStore;

      heightPixels = displayMetrics.heightPixels;
      widthPixels = displayMetrics.widthPixels;

      heightDip = (int) (heightPixels / displayMetrics.density);
      widthDip = (int) (widthPixels / displayMetrics.density);
      smallestWidthDp = Math.min(heightDip, widthDip);

      displayRealSize = getDisplayRealSize(context);

      // device screen size in inches
      float screenWidth = displayRealSize.x / displayMetrics.xdpi;
      float screenHeight = displayRealSize.y / displayMetrics.ydpi;
      diagonalScreenSize = (float) Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));

      // status/navigation bar height
      statusBarHeight = getAndroidResource(res, "status_bar_height");
      navigationBarHeight = getAndroidResource(res, "navigation_bar_height");

      di = new DeviceInfo(
            is7inch,
            is10inch,
            isPortrait,
            isLandscape,
            isPhone,
            isTablet,
            isMultiWindow,
            isPiP,
            isAmazon,
            heightPixels,
            widthPixels,
            heightDip,
            widthDip,
            smallestWidthDp,
            statusBarHeight,
            navigationBarHeight,
            diagonalScreenSize,
            displayRealSize,
            (Application) context.getApplicationContext());

      CACHE.put(context, di);
      return di;
   }

   /**
    * @param base
    * @return
    */
   private static Context getActivityContext(Context base) {
      Context c = base;
      while (c instanceof ContextWrapper && !(c instanceof Activity)) {
         c = ((ContextWrapper) c).getBaseContext();
      }
      if (c instanceof Activity) return c;
      else return null;
   }

   /**
    * Display real size is also annoying
    *
    * @param context
    * @return
    */
   private static Point getDisplayRealSize(Context context) {

      WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      if (windowManager == null) {
         return getNonNullDisplaySize();
      }
      Display display = windowManager.getDefaultDisplay();
      if (display == null) {
         return getNonNullDisplaySize();
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
         return getDisplaySize_API17(display);
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
         return getDisplaySize_API13(display);
      } else {
         return getDisplaySize(display);
      }
   }

   @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
   private static Point getDisplaySize_API17(Display display) {
      Point p = new Point();
      display.getRealSize(p);
      return p;
   }

   @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
   private static Point getDisplaySize_API13(Display display) {
      Point p = new Point();
      display.getSize(p);
      return p;
   }

   @TargetApi(Build.VERSION_CODES.N)
   private static boolean getMultiWindow(Activity activity) {
      return activity.isInMultiWindowMode();
   }

   @TargetApi(Build.VERSION_CODES.N)
   private static boolean getPip(Activity activity) {
      return activity.isInPictureInPictureMode();
   }

   private static Point getDisplaySize(Display display) {
      return new Point(display.getWidth(), display.getHeight());
   }

   private static Point getNonNullDisplaySize() {
      return new Point(1, 1);
   }

   private static int getAndroidResource(Resources r, String id) {
      int resourceId = r.getIdentifier(id, "dimen", "android");
      return resourceId > 0 ? r.getDimensionPixelSize(resourceId) : 0;
   }

   /*
    * display metrics can be `null` in a bunch of situations,
    * so we'll try every possibility until we find one that works
    *
    */
   private static DisplayMetrics getDisplayMetrics(Context context) {

      // easier way, resources
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      if (dm != null) return dm;

      // create one and dump from the default display
      dm = new DisplayMetrics();
      WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      if (windowManager != null) {
         Display d = windowManager.getDefaultDisplay();
         if (d != null) {
            d.getMetrics(dm);
            return dm;
         }
      }

      // everything failed, set some values just to avoid null
      dm.setToDefaults();
      return dm;
   }
}

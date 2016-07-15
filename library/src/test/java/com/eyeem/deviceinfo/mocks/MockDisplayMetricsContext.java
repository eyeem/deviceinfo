package com.eyeem.deviceinfo.mocks;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by budius on 14.07.16.
 */
public class MockDisplayMetricsContext {

   public static Context get(int width, int height, float dpi) {
      BaseMockContext.BaseMockResources r = new DisplayMetricsMockResources(width, height, dpi);
      BaseMockContext.BaseMockPackageManager p = new BaseMockContext.BaseMockPackageManager();
      BaseMockContext.BaseMockApplication a = new BaseMockContext.BaseMockApplication(r, p);
      return new BaseMockContext(a, r, p);
   }


   static class DisplayMetricsMockResources extends BaseMockContext.BaseMockResources {

      final int width;
      final int height;
      final float dpi;

      DisplayMetricsMockResources(int width, int height, float dpi) {
         this.width = width;
         this.height = height;
         this.dpi = dpi;
      }

      @Override public DisplayMetrics getDisplayMetrics() {
         DisplayMetrics d = new DisplayMetrics();
         d.widthPixels = width;
         d.heightPixels = height;
         d.xdpi = dpi;
         d.ydpi = dpi;
         return d;
      }
   }
}
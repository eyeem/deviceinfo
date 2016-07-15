package com.eyeem.deviceinfo.mocks;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by budius on 14.07.16.
 */
public class MockDisplayContext {

   public static Context get(Display display) {
      BaseMockContext.BaseMockResources r = new BaseMockContext.BaseMockResources();
      BaseMockContext.BaseMockPackageManager p = new BaseMockContext.BaseMockPackageManager();
      BaseMockContext.BaseMockApplication a = new MockWindowManagerApplication(r, p, display);
      return new BaseMockContext(a, r, p);
   }

   static class MockWindowManagerApplication extends BaseMockContext.BaseMockApplication {

      final Display display;

      MockWindowManagerApplication(BaseMockContext.BaseMockResources resources, BaseMockContext.BaseMockPackageManager packageManager, Display display) {
         super(resources, packageManager);
         this.display = display;
      }

      @Override public Object getSystemService(String name) {
         if (WINDOW_SERVICE.equals(name)) {
            return new WindowManager() {
               @Override public Display getDefaultDisplay() {
                  System.out.println("Returning display. W: " +display.getWidth());
                  return display;
               }

               @Override public void removeViewImmediate(View view) {/**/}

               @Override public void addView(View view, ViewGroup.LayoutParams params) {/**/}

               @Override
               public void updateViewLayout(View view, ViewGroup.LayoutParams params) {/**/}

               @Override public void removeView(View view) {/**/}
            };
         } else {
            return super.getSystemService(name);
         }
      }
   }

}

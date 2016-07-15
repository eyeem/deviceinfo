package com.eyeem.deviceinfo.mocks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.test.mock.MockPackageManager;
import android.test.mock.MockResources;
import android.util.DisplayMetrics;

/**
 * Created by budius on 14.07.16.
 */
public class BaseMockContext extends MockContext {

   final BaseMockApplication application;
   final BaseMockResources resources;
   final BaseMockPackageManager packageManager;

   public  static Context base() {
      BaseMockResources r = new BaseMockResources();
      BaseMockPackageManager p = new BaseMockPackageManager();
      BaseMockApplication a = new BaseMockApplication(r, p);
      return new BaseMockContext(a, r, p);
   }

   public BaseMockContext(BaseMockApplication application, BaseMockResources resources, BaseMockPackageManager packageManager) {
      this.application = application;
      this.resources = resources;
      this.packageManager = packageManager;
   }

   @Override public Object getSystemService(String name) {
      return application.getSystemService(name);
   }

   @Override public Resources getResources() {
      return resources;
   }

   @Override public Context getApplicationContext() {
      return application;
   }

   @Override public PackageManager getPackageManager() {
      return packageManager;
   }

   @Override public String getPackageName() {
      return "mocked";
   }

   public static class BaseMockPackageManager extends MockPackageManager {
      @Override public String getInstallerPackageName(String packageName) {
         return "";
      }
   }

   public static class BaseMockApplication extends MockApplication {
      final BaseMockResources resources;
      final BaseMockPackageManager packageManager;

      BaseMockApplication(BaseMockResources resources, BaseMockPackageManager packageManager) {
         this.resources = resources;
         this.packageManager = packageManager;
      }

      @Override public Resources getResources() {
         return resources;
      }

      @Override public PackageManager getPackageManager() {
         return packageManager;
      }

      @Override public String getPackageName() {
         return "mocked";
      }

      @Override public Object getSystemService(String name) {
         if (Context.WINDOW_SERVICE.equals(name)) {
            return null;
         } else {
            return super.getSystemService(name);
         }
      }

      @Override public Context getApplicationContext() {
         return this;
      }
   }

   public static class BaseMockResources extends MockResources {
      @Override public DisplayMetrics getDisplayMetrics() {
         return new DisplayMetrics();
      }

      @Override public int getIdentifier(String name, String defType, String defPackage) {
         return -1;
      }

      @Override public boolean getBoolean(int id) throws NotFoundException {
         return false;
      }
   }
}

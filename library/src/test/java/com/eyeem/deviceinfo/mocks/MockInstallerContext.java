package com.eyeem.deviceinfo.mocks;

import android.content.Context;

/**
 * Created by budius on 14.07.16.
 */
public class MockInstallerContext {

   public static Context get(String installerName) {
      BaseMockContext.BaseMockResources r = new BaseMockContext.BaseMockResources();
      BaseMockContext.BaseMockPackageManager p = new MockInstallerPackageManager(installerName);
      BaseMockContext.BaseMockApplication a = new BaseMockContext.BaseMockApplication(r, p);
      return new BaseMockContext(a, r, p);
   }

   static class MockInstallerPackageManager extends BaseMockContext.BaseMockPackageManager {

      final String installerName;

      private MockInstallerPackageManager(String installerName) {
         this.installerName = installerName;
      }

      @Override public String getInstallerPackageName(String packageName) {
         return installerName;
      }
   }
}

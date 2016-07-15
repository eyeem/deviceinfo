package com.eyeem.deviceinfo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.eyeem.deviceinfo.mocks.BaseMockContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by budius on 15.07.16.
 */
public class DeviceInfoCacheTest {
   @Before public void beforeTest() throws Exception {
      // required if we don't want to crash
      Utils.setManufacturer("unknown");
   }

   @Test public void cachedDeviceInfo() throws Exception {
      Context c = getNewMockedActivity();
      DeviceInfo di1 = DeviceInfo.get(c);
      DeviceInfo di2 = DeviceInfo.get(c);
      assertEquals(di1, di2);
   }

   @Test public void notCachedDeviceInfo() throws Exception {
      Context c1 = getNewMockedActivity();
      Context c2 = getNewMockedActivity();
      DeviceInfo di1 = DeviceInfo.get(c1);
      DeviceInfo di2 = DeviceInfo.get(c2);
      assertNotEquals(di1, di2);
   }

   private Context getNewMockedActivity() {
      final Context c = BaseMockContext.base();
      return new Activity() {
         @Override public Resources getResources() {
            return c.getResources();
         }

         @Override public Context getApplicationContext() {
            return c.getApplicationContext();
         }

         @Override public Object getSystemService(@NonNull String name) {
            return c.getSystemService(name);
         }

         @Override public PackageManager getPackageManager() {
            return c.getPackageManager();
         }

         @Override public String getPackageName() {
            return c.getPackageName();
         }
      };
   }

}

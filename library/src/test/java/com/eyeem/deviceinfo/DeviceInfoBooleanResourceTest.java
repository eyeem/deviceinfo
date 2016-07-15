package com.eyeem.deviceinfo;

import android.os.Build;

import com.eyeem.deviceinfo.mocks.BaseMockContext;
import com.eyeem.deviceinfo.mocks.MockBooleanContext;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by budius on 14.07.16.
 */
public class DeviceInfoBooleanResourceTest {

   @Before public void beforeTest() throws Exception {
      // required if we don't want to crash
      Utils.setManufacturer("unknown");
   }

   @Test public void landscape() throws Exception {
      DeviceInfo d = DeviceInfo.get(MockBooleanContext.get(R.bool.isPortrait, false));
      assertTrue(d.isLandscape);
   }

   @Test public void portrait() throws Exception {
      DeviceInfo d = DeviceInfo.get(MockBooleanContext.get(R.bool.isPortrait, true));
      assertTrue(d.isPortrait);
   }

   @Test public void is7inch() throws Exception {
      DeviceInfo d = DeviceInfo.get(MockBooleanContext.get(R.bool.is7inch, true));
      assertTrue(d.is7inch);
   }

   @Test public void is10inch() throws Exception {
      DeviceInfo d = DeviceInfo.get(MockBooleanContext.get(R.bool.is10inch, true));
      assertTrue(d.is10inch);
   }

   @Test public void isPhone() throws Exception {
      DeviceInfo d = DeviceInfo.get(BaseMockContext.base());
      assertTrue(d.isPhone);
   }

   @Test public void isTablet() throws Exception {
      DeviceInfo d = DeviceInfo.get(MockBooleanContext.get(R.bool.is10inch, true));
      assertTrue(d.isTablet);
   }

   @Test public void isNotAmazon() throws Exception {
      DeviceInfo d = DeviceInfo.get(BaseMockContext.base());
      assertFalse(d.isAmazon);
   }
}

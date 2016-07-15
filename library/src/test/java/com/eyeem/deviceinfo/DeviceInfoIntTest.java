package com.eyeem.deviceinfo;

import com.eyeem.deviceinfo.mocks.MockDimPixelSizeContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by budius on 14.07.16.
 */
public class DeviceInfoIntTest {

   @Before public void beforeTest() throws Exception {
      // required if we don't want to crash
      Utils.setManufacturer("unknown");
   }

   @Test public void statusBar() throws Exception {
      for (int i = 20; i < 997; i = i + 137) {
         // test several width sizes, make sure pass is not accidental
         DeviceInfo di = DeviceInfo.get(MockDimPixelSizeContext.get("status_bar_height", i));
         assertEquals(di.statusBarHeight, i);
      }
   }

   @Test public void NavigationBar() throws Exception {
      for (int i = 20; i < 997; i = i + 137) {
         // test several width sizes, make sure pass is not accidental
         DeviceInfo di = DeviceInfo.get(MockDimPixelSizeContext.get("navigation_bar_height", i));
         assertEquals(di.navigationBarHeight, i);
      }
   }
}

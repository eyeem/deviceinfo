package com.eyeem.deviceinfo;

import com.eyeem.deviceinfo.mocks.MockDisplayMetricsContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by budius on 14.07.16.
 */
public class DeviceInfoDisplayTest {

   @Before public void beforeTest() throws Exception {
      // required if we don't want to crash
      Utils.setManufacturer("unknown");
   }

   @Test public void width() throws Exception {
      for (int i = 0; i < 997; i = i + 137) {
         // test several width sizes, make sure pass is not accidental
         DeviceInfo di = DeviceInfo.get(MockDisplayMetricsContext.get(i, 0, 1));
         assertEquals(i, di.widthPixels);
         assertEquals(0, di.heightPixels);
      }
   }

   @Test public void height() throws Exception {
      for (int i = 0; i < 997; i = i + 137) {
         // test several width sizes, make sure pass is not accidental
         DeviceInfo di = DeviceInfo.get(MockDisplayMetricsContext.get(0, i, 1));
         assertEquals(i, di.heightPixels);
         assertEquals(0, di.widthPixels);
      }
   }
}

package com.eyeem.deviceinfo;

import com.eyeem.deviceinfo.mocks.MockInstallerContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by budius on 14.07.16.
 */
public class DeviceInfoAmazonTest {

   @Before public void beforeTest() throws Exception {
      Utils.setManufacturer("amazon");
   }

   @Test public void amazon() throws Exception {
      DeviceInfo di = DeviceInfo.get(MockInstallerContext.get("com.amazon.venezia"));
      assertTrue(di.isAmazon);
   }

}

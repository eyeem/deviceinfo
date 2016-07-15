package com.eyeem.deviceinfo.mocks;

import android.content.Context;

import java.util.Random;

/**
 * Created by budius on 14.07.16.
 */
public class MockDimPixelSizeContext {
   public static Context get(String name, int value) {
      BaseMockContext.BaseMockResources r = new MockIntResources(name, value);
      BaseMockContext.BaseMockPackageManager p = new BaseMockContext.BaseMockPackageManager();
      BaseMockContext.BaseMockApplication a = new BaseMockContext.BaseMockApplication(r, p);
      return new BaseMockContext(a, r, p);
   }

   static class MockIntResources extends BaseMockContext.BaseMockResources {
      final int id = new Random().nextInt(2147483640) + 1;
      final String name;
      final int value;

      MockIntResources(String name, int value) {
         this.name = name;
         this.value = value;
      }

      @Override public int getDimensionPixelSize(int id) throws NotFoundException {
         if (this.id == id) {
            return value;
         } else {
            return -1;
         }
      }

      @Override public int getIdentifier(String name, String defType, String defPackage) {
         if (this.name.equals(name)) {
            return id;
         } else {
            return super.getIdentifier(name, defType, defPackage);
         }
      }
   }


}

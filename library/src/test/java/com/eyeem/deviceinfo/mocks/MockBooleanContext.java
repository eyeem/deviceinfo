package com.eyeem.deviceinfo.mocks;

import android.content.Context;

/**
 * Created by budius on 14.07.16.
 */
public class MockBooleanContext {

   public  static Context get(int id, boolean value) {
      BaseMockContext.BaseMockResources r = new MockBooleanResources(id, value);
      BaseMockContext.BaseMockPackageManager p = new BaseMockContext.BaseMockPackageManager();
      BaseMockContext.BaseMockApplication a = new BaseMockContext.BaseMockApplication(r, p);
      return new BaseMockContext(a, r, p);
   }

   static class MockBooleanResources extends BaseMockContext.BaseMockResources {
      final int id;
      final boolean value;

      MockBooleanResources(int id, boolean value) {
         this.id = id;
         this.value = value;
      }

      @Override public boolean getBoolean(int id) throws NotFoundException {
         return (this.id == id) ? value : super.getBoolean(id);
      }
   }

}

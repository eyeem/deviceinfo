package com.eyeem.deviceinfo;

import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by budius on 14.07.16.
 */
public class Utils {

   public static void setManufacturer(String manufacturer) throws Exception {
      Field field = Build.class.getDeclaredField("MANUFACTURER");
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      //boolean isModifierAccessible = modifiersField.isAccessible();
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      //boolean isAccessible = field.isAccessible();
      field.setAccessible(true);
      field.set(null, manufacturer);
   }

   public static void setOsVersion(int version) throws Exception {
      Field field = Build.VERSION.class.getField("SDK_INT");
      field.setAccessible(true);
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      field.set(null, version);
   }

}

package com.eyeem.deviceinfo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by budius on 07.07.15.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class ContextWatcher implements Application.ActivityLifecycleCallbacks {

   private final Application app;
   private final ArrayList<WeakEquals<Activity>> created = new ArrayList<>();
   private Activity current;

   ContextWatcher(Application app) {
      this.app = app;
      this.app.registerActivityLifecycleCallbacks(this);
   }

   /**
    * Returns the device info object for the current (last created) activity.
    * If none activity is running, we still return a valid DeviceInfo,
    * but calculations that depends on `Display` won't be precise or necessarily correct.
    *
    * @return valid device info object, never null.
    */
   Context getCurrentContext() {

      Context c;

      // first, it tries to use the current resumed activity
      c = current;

      // then , it tries to find the last created activity
      while (created.size() > 0 && c == null) {
         c = created.get(0).get();
         if (c == null) {
            created.remove(0);
         }
      }

      // last resource, uses the app reference
      if (c == null) {
         c = app;
      }

      return c;
   }

   @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
      created.add(0, new WeakEquals<>(activity));
   }

   @Override public void onActivityDestroyed(Activity activity) {
      created.remove(new WeakEquals<>(activity));

      // also checks for dead references on the array
      for (int i = created.size() - 1; i >= 0; i--) {
         if (created.get(i).get() == null) {
            created.remove(i);
         }
      }
   }

   @Override public void onActivityResumed(Activity activity) {
      current = activity;
   }

   @Override public void onActivityPaused(Activity activity) {
      current = null;
   }

   // region not needed callbacks
   @Override public void onActivityStarted(Activity activity) {/**/}

   @Override public void onActivityStopped(Activity activity) {/**/}

   @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}
   // endregion

   /**
    * WeakReference that overrides `boolean equals(Object)` to compare on its references.
    *
    * @param <T>
    */
   private static class WeakEquals<T> extends WeakReference<T> {
      public WeakEquals(T r) {
         super(r);
      }

      @Override public boolean equals(Object o) {

         if (o instanceof Reference) {
            o = ((Reference) o).get();
         }

         T t = get();
         return t != null && o != null && t.equals(o);
      }
   }

}

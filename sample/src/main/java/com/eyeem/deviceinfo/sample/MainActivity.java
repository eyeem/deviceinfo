package com.eyeem.deviceinfo.sample;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eyeem.deviceinfo.DeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

   @Bind(R.id.is7inch) TextView is7inch;
   @Bind(R.id.is10inch) TextView is10inch;
   @Bind(R.id.isPortrait) TextView isPortrait;
   @Bind(R.id.isLandscape) TextView isLandscape;
   @Bind(R.id.isPhone) TextView isPhone;
   @Bind(R.id.isTablet) TextView isTablet;
   @Bind(R.id.isAmazon) TextView isAmazon;
   @Bind(R.id.heightPixels) TextView heightPixels;
   @Bind(R.id.widthPixels) TextView widthPixels;
   @Bind(R.id.statusBarHeight) TextView statusBarHeight;
   @Bind(R.id.navigationBarHeight) TextView navigationBarHeight;
   @Bind(R.id.diagonalScreenSize) TextView diagonalScreenSize;
   @Bind(R.id.displayRealSize) TextView displayRealSize;
   @Bind(R.id.isWifiConnection) TextView isWifiConnection;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      ButterKnife.bind(this);
      fillInfo();
   }

   @Override public boolean onCreateOptionsMenu(Menu menu) {
      MenuItem mi = menu.add("Refresh");
      MenuItemCompat.setShowAsAction(mi, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
      return true;
   }

   @Override public boolean onOptionsItemSelected(MenuItem item) {
      fillInfo();
      return true;
   }

   private void fillInfo() {
      DeviceInfo di = DeviceInfo.get(this);
      is7inch.setText(String.valueOf(di.is7inch));
      is10inch.setText(String.valueOf(di.is10inch));
      isPortrait.setText(String.valueOf(di.isPortrait));
      isLandscape.setText(String.valueOf(di.isLandscape));
      isPhone.setText(String.valueOf(di.isPhone));
      isTablet.setText(String.valueOf(di.isTablet));
      isAmazon.setText(String.valueOf(di.isAmazon));
      heightPixels.setText(String.valueOf(di.heightPixels));
      widthPixels.setText(String.valueOf(di.widthPixels));
      statusBarHeight.setText(String.valueOf(di.statusBarHeight));
      navigationBarHeight.setText(String.valueOf(di.navigationBarHeight));
      diagonalScreenSize.setText(String.valueOf(di.diagonalScreenSize));
      displayRealSize.setText(String.valueOf(di.getDisplayRealSize()));
      isWifiConnection.setText(String.valueOf(di.isWifiConnection()));
   }
}

# DeviceInfo
Easy info about Android device [ ![Download](https://api.bintray.com/packages/eyeem/maven/deviceinfo/images/download.svg) ](https://bintray.com/eyeem/maven/deviceinfo/_latestVersion)

(currently) available info:
- is7inch;
- is10inch;
- isPortrait;
- isLandscape;
- isPhone;
- isTablet;
- isInMultiWindowMode;
- isInPictureInPictureMode;
- isAmazon;
- heightPixels;
- heightDip;
- widthPixels;
- widthDip;
- smallestWidthDp;
- statusBarHeight;
- navigationBarHeight;
- diagonalScreenSize;
- displayRealSize;

```Java
// add to your dependencies
compile 'com.eyeem.deviceinfo:library:<latest>'
```

```Java
// get reference to latest info using context, activity, or view
DeviceInfo di = DeviceInfo.get(context);
// or
DeviceInfo di = DeviceInfo.get(view);

// use as needed
if(di.isPortrait) {
   view.getLayoutParams.width = di.widthPixels;
}
```

Pull requests welcome!

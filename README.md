Plus1 WapStart Conversion Android SDK
=====================================

Is an open source library for integrating the mechanism of conversion count in the [Plus1 WapStart](https://plus1.wapstart.ru) network for your Android application.

The library is distributed under a free license BSD (as is).

# Installation and Setup

1. Download the latest version of the SDK:  https://github.com/WapStart/plus1-conversion-android-sdk/tags
2. Add the SDK into your project as a library.

# Using the SDK

Let's take a look at the configuration of the application for counting conversions using application [HelloConversion](https://github.com/WapStart/plus1-conversion-android-sdk/blob/master/examples/HelloConversion/) as an example.

The first step is adding custom url-circuit and the host. It is a necessary action in order to be able to return to the App from Browser after counting the action. Scheme and host must be added to the _manifest_ of your application. 

Example:
```XML
    <intent-filter>
        <data android:scheme="wsp1hc" android:host="ru.wapstart.plus1.conversion.hello" />
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
    </intent-filter>
```

For the test application scheme is wsp1hc://

**Attention:** scheme-host combination must be unique, to provide the return to your application.

In addition, for the correct work SDK, application must be eligible for Internet connection.

```XML
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Once you come up with the scheme and host, you need to initialize the counter conversions. It is initializied by two parameters: id counter and a link in order to return to the application.

Counter ID can be found on the **Conversion page**.

Example:

```Java
     new Plus1ConversionTracker(this)
		.setTrackId(/* Place your WapStart Plus1 conversion track id here */)
        .setCallbackUrl("wsp1hc://ru.wapstart.plus1.conversion.hello")
        .run();
```

Counter conversions can traverse two types of exceptions:

1. Plus1ConversionTracker.CallbackUrlNotDefinedException, if you do not specify a url for return;
2. Plus1ConversionTracker.TrackIdNotDefinedException, if you do not specify the id counter.

In first run of the counter, your browser opens a specially crafted link that will redirect the user bach to the application back. The same is true in the case of Internet connection. Otherwise, the browser will not be opened and the counting of the target action will be postponed until the next time.

# Contact information 
E-Mail: clientsupport@co.wapstart.ru  
ICQ: 553425962

Plus1 WapStart Conversion Android SDK
=====================================

Это open source библиотека для интеграции механизма подсчета конверсии в сети [Plus1 WapStart](https://plus1.wapstart.ru) в ваши Android-приложени.

Plus1 WapStart Conversion Android SDK распространяется под свободной лицензией BSD (as is).

# Установка и настройка

1. Скачайте последнюю версию SDK: https://github.com/WapStart/plus1-conversion-android-sdk/tags
2. Добавьте SDK в свой проект в качестве библиотеки.

# Использование SDK

Рассмотрим настройку приложения для подсчета конверсий на примере [HelloConversion](https://github.com/WapStart/plus1-conversion-android-sdk/blob/master/examples/HelloConversion/)

В первую очередь необходимо добавить собственную url-схему и хост. Это необходимо для возврата из браузера обратно в приложение после учета целевого действия. Схему/хост необходимо добавить в _manifest_ вашего приложения. 

Пример:
```XML
    <intent-filter>
        <data android:scheme="wsp1hc" android:host="ru.wapstart.plus1.conversion.hello" />
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
    </intent-filter>
```

Для тестового приложения в качестве схемы выступает wsp1hc://

**Внимание:** сочетание схема-хост должно быть уникально, для гарантии возврата в ваше приложение.

Кроме того, для корректной работы SDK приложение должно обладать правом на получение состояния интернет-подключения.

```XML
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

После того, как вы придумали схему/хост, необходимо инициализировать счетчик конверсий. Инициализируется он двумя параметрами: id счетчика и ссылкой для возврата в приложение.

Идентификатор счетчика можно узнать на странице **Конверсия**.

Пример:

```Java
     new Plus1ConversionTracker(this)
		.setTrackId(/* Place your WapStart Plus1 conversion track id here */)
        .setCallbackUrl("wsp1hc://ru.wapstart.plus1.conversion.hello")
        .run();
```

Счетчик конверсий может пробросить 2 вида исключений:

1. Plus1ConversionTracker.CallbackUrlNotDefinedException, если вы не укажете url для возврата;
2. Plus1ConversionTracker.TrackIdNotDefinedException, если вы не укажете id счетчика. 

При первом запуске приложения счетчик сработает, в браузере откроется специально сформированная ссылка, откуда пользователя перенаправит обратно в приложение по указанной вами ссылке. Вышесказанное верно в случае наличия интернет-подключения. В противном случае открытия браузера не произойдет и подсчет целевого действия будет отложен до следующего раза.

# Контактная информация
По всем возникающим у вас вопросам интеграции вы можете обратиться в службу поддержки пользователей:  
E-Mail: clientsupport@co.wapstart.ru  
ICQ: 553425962

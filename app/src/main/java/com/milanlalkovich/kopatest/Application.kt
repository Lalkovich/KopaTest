package com.milanlalkovich.kopatest
import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

/**
 *  Created by Android Studio on 15.02.2021 21:23
 *  Developer: Dima Iakubenko
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
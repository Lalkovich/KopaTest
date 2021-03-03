package com.milanlalkovich.kopatest
import android.app.Application
import com.example.kopashop.di.firebaseDataSourceModule
import com.milanlalkovich.kopatest.di.repositoriesModule
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.milanlalkovich.kopatest.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *  Created by Android Studio on 15.02.2021 21:23
 *  Developer: Dima Iakubenko
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@Application)
            androidLogger()
            modules(
                firebaseDataSourceModule,
                repositoriesModule,
                viewModelsModule
            )}

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
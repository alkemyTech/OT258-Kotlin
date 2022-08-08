package com.melvin.ongandroid

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppController:Application() {

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
    }
}
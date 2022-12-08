package com.joshgm3z.smsrelay

import android.app.Application
import com.joshgm3z.smsrelay.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SmsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SmsApplication)
            modules(appModule)
        }
    }
}
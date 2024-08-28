package com.cyberkaidev.bankyou

import android.app.Application
import com.cyberkaidev.bankyou.data.di.appModule
import com.cyberkaidev.bankyou.data.di.networkModule
import com.cyberkaidev.bankyou.data.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BankYouApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BankYouApplication)
            modules(appModule, networkModule, storageModule)
        }
    }
}
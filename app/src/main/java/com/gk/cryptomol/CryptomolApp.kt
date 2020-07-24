package com.gk.cryptomol

import android.app.Application
import com.gk.cryptomol.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class CryptomolApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{androidContext(this@CryptomolApp)}
        loadKoinModules(appModule)
    }
}
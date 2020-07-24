package com.gk.cryptomol.di

import androidx.room.Room
import com.gk.cryptomol.R
import com.gk.cryptomol.database.CryptomolDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CryptomolDatabase::class.java,
            androidApplication().baseContext.getString(R.string.app_name)
        ).build()
    }

    single {
        get<CryptomolDatabase>().getCoinDao()
    }
}

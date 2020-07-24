package com.gk.cryptomol.di

import com.gk.cryptomol.repository.CoinRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        CoinRepository(get(), get ())
    }
}
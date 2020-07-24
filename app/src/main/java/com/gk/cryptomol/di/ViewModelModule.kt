package com.gk.cryptomol.di

import com.gk.cryptomol.ui.coindetail.CoinDetailViewModel
import com.gk.cryptomol.ui.favorites.FavoriteViewModel
import com.gk.cryptomol.ui.home.HomeViewModel
import com.gk.cryptomol.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { CoinDetailViewModel(get()) }
}
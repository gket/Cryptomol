package com.gk.cryptomol.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.cryptomol.data.CoinItem
import com.gk.cryptomol.repository.CoinRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CoinRepository) : ViewModel() {

    var allCoins = MutableLiveData<List<CoinItem>>()
    var searchResult = MutableLiveData<List<CoinItem>>()

    fun getCoinList() = viewModelScope.launch {
        allCoins.value = repository.getCoinList()
    }

    fun getSearchResult(searchQuery : String?) = viewModelScope.launch {
        searchResult.value = repository.getSearchResult(searchQuery)
    }

}
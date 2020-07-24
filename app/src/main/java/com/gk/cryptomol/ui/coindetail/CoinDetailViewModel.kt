package com.gk.cryptomol.ui.coindetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.cryptomol.data.CoinDetail
import com.gk.cryptomol.repository.CoinRepository
import com.gk.cryptomol.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinDetailViewModel(private val repository: CoinRepository) : ViewModel() {

    var coinDetailResource = MutableLiveData<Resource<CoinDetail>>()

    fun getCoinDetail(id: String, interval: Long) {
        viewModelScope.launch {
            delay(interval)
            coinDetailResource.value = Resource.Loading()
            val response = repository.getCoinDetails(id)
            handleCoinDetailResponse(response)
        }
    }

    private fun handleCoinDetailResponse(response: Response<CoinDetail>) {
        if (response.isSuccessful) {
            response.body().let { coinDetail ->
                coinDetail?.let {
                    coinDetailResource.value = Resource.Success(it)
                }
            }
        } else if(response.message().isNotEmpty()) {
            coinDetailResource.value = Resource.Error(response.message())
        }
    }

}
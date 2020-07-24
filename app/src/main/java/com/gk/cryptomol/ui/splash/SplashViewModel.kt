package com.gk.cryptomol.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.cryptomol.data.Coin
import com.gk.cryptomol.repository.CoinRepository
import com.gk.cryptomol.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SplashViewModel(
    private val repository: CoinRepository
) :
    ViewModel() {

    var coinResponse = MutableLiveData<Resource<Coin>>()

    fun getAllCoins() {
        viewModelScope.launch {
            try {
                coinResponse.postValue(Resource.Loading())
                val response = repository.getCoins()
                handleCoinResponse(response)
            }
            catch (e : Exception){
                Log.d("CoinResponseExc : ", e.message.toString())
            }
        }
    }

    suspend fun handleCoinResponse(response: Response<Coin>) {
        if (response.isSuccessful) {
            response.body().let { coins ->
                coins?.let {
                 withContext(Dispatchers.IO){
                       repository.addCoins(coins)
                       coinResponse.postValue(Resource.Success(it))
                   }
                }
            }
        }
        else{
            coinResponse.postValue(Resource.Error(response.message()))
        }
    }
}
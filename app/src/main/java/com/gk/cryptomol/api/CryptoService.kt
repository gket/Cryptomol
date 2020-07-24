package com.gk.cryptomol.api

import com.gk.cryptomol.data.Coin
import com.gk.cryptomol.data.CoinDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("api/v3/coins/list")
    suspend fun getCoins() : Response<Coin>

    @GET("api/v3/coins/{id}")
    suspend fun getCoinDetail(
        @Path("id")
        coinId : String,
        @Query("market_data")
        marketData: Boolean = true
    ) : Response<CoinDetail>
}
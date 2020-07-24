package com.gk.cryptomol.data

import com.google.gson.annotations.SerializedName

data class CoinDetail(
    @SerializedName("description")
    val description: Description,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String,
    @SerializedName("id")
    val coinId: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("market_data")
    val marketData: MarketData,
    @SerializedName("name")
    val coinName: String,
    @SerializedName("symbol")
    val coinSymbol: String
)
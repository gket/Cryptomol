package com.gk.cryptomol.data

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrentPrice,
    @SerializedName("price_change_24h")
    val priceChangeDaily: Double
)
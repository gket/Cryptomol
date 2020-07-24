package com.gk.cryptomol.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "coins"
)

data class CoinItem(
    @PrimaryKey
    @SerializedName("id")
    val coinId: String,
    @SerializedName("name")
    val coinName: String,
    @SerializedName("symbol")
    val coinSymbol: String
)
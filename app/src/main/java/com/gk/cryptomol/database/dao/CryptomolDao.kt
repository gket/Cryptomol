package com.gk.cryptomol.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gk.cryptomol.data.CoinItem

@Dao
interface CryptomolDao {

    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): List<CoinItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoins(coins: List<CoinItem>)

    @Query("SELECT * FROM coins where coinName like :searchQuery OR coinSymbol like :searchQuery")
    suspend fun getSearchResults(searchQuery : String?) : List<CoinItem>

}
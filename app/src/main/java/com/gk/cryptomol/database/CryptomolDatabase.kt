package com.gk.cryptomol.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gk.cryptomol.data.CoinItem
import com.gk.cryptomol.database.dao.CryptomolDao

@Database(entities = [CoinItem::class], version = 1, exportSchema = false)
abstract class CryptomolDatabase : RoomDatabase(){
    abstract fun getCoinDao(): CryptomolDao
}
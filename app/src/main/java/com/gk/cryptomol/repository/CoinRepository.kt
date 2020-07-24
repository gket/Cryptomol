package com.gk.cryptomol.repository

import com.gk.cryptomol.api.CryptoService
import com.gk.cryptomol.data.CoinItem
import com.gk.cryptomol.database.dao.CryptomolDao

class CoinRepository(private val cryptoService: CryptoService, private val cryptomolDao: CryptomolDao) {
    suspend fun getCoins() = cryptoService.getCoins()
    suspend fun getCoinDetails(id : String) = cryptoService.getCoinDetail(id)
    suspend fun addCoins(coins : List<CoinItem>) = cryptomolDao.addCoins(coins)
    suspend fun getCoinList() : List<CoinItem> = cryptomolDao.getAllCoins()
    suspend fun getSearchResult(searchQuery : String?) : List<CoinItem> = cryptomolDao.getSearchResults(searchQuery)

}
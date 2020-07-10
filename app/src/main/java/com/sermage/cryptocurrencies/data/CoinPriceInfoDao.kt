package com.sermage.cryptocurrencies.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sermage.cryptocurrencies.pojo.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE )
    fun insertCoinPriceInfo(list:List<CoinPriceInfo>)
    @Query("SELECT* FROM coin_price_list ORDER BY lastUpdate DESC")
    fun getPriceList():LiveData<List<CoinPriceInfo>>
    @Query("SELECT*FROM coin_price_list WHERE fromSymbol=:fsym LIMIT 1")
    fun getPriceInfoAboutCoin(fsym:String):LiveData<CoinPriceInfo>
}
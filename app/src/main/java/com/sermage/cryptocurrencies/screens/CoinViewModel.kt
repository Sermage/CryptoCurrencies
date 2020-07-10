package com.sermage.cryptocurrencies.screens

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.sermage.cryptocurrencies.api.ApiFactory
import com.sermage.cryptocurrencies.data.AppDatabase
import com.sermage.cryptocurrencies.pojo.CoinInfoRawData
import com.sermage.cryptocurrencies.pojo.CoinPriceInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application):AndroidViewModel(application) {

    private val compositeDisposable=CompositeDisposable()
    private val db=AppDatabase.getInstance(application)
    val priceList=db.coinPriceInfoDao().getPriceList()

    init{
        loadData()
    }

    fun getCoinInfo(fSym:String):LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData(){
        val disposable= ApiFactory.ApiService.getTopCoinList(limit = 20)
            .map{it.data?.map { it.coinInfo?.name}?.joinToString (",")}
            .flatMap { ApiFactory.ApiService.getCoinInfoRawData(fSyms =it)}
            .map { getCoinPriceInfoFromRaw(it) }
            .repeat()
            .retry()
            .delaySubscription(10,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertCoinPriceInfo(it)
            },{
                Log.i("TEST",it.message)
            })
        compositeDisposable.add(disposable)
    }

    private fun getCoinPriceInfoFromRaw(coinInfoRawData: CoinInfoRawData):List<CoinPriceInfo>{
        val result= arrayListOf<CoinPriceInfo>()
        val jsonObject=coinInfoRawData.coinInfoJsonObject?:return result
        val coinKeys=jsonObject.keySet()
        for(coinKey in coinKeys){
            val currencyJson=jsonObject.getAsJsonObject(coinKey)
            val currencyKeys=currencyJson.keySet()
            for(key in currencyKeys){
                val priceInfo= Gson().fromJson(currencyJson.getAsJsonObject(key),CoinPriceInfo::class.java)
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
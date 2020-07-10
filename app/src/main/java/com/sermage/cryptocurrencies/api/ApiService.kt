package com.sermage.cryptocurrencies.api

import com.sermage.cryptocurrencies.pojo.CoinInfoRawData
import com.sermage.cryptocurrencies.pojo.ListOfDatums
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinList(
        @Query(PARAM_LIMIT) limit: Int = 10,
        @Query(PARAM_TSYM) tSym:String= CURRENCY,
        @Query(PARAM_API_KEY) apiKey:String=""
        ): Single<ListOfDatums>

    @GET("pricemultifull")
    fun getCoinInfoRawData(
        @Query(PARAM_FSYMS) fSyms: String,
        @Query(PARAM_TSYMS) tSyms:String= CURRENCY,
        @Query(PARAM_API_KEY) apiKey:String=""
    ): Single<CoinInfoRawData>

    companion object {
    private const val PARAM_LIMIT="limit"
        private const val PARAM_TSYM="tsym"
        private const val PARAM_API_KEY="api_key"
        private const val PARAM_FSYMS="fsyms"
        private const val PARAM_TSYMS="tsyms"
        private const val CURRENCY="USD"

    }
}
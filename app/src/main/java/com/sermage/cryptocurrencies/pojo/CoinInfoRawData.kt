package com.sermage.cryptocurrencies.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinInfoRawData (
    @SerializedName("RAW")
    @Expose
    val coinInfoJsonObject:JsonObject?=null
)
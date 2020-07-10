package com.sermage.cryptocurrencies.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(stamp: Long?): String {
    if (stamp == null) return ""
    val timestamp=Timestamp(stamp*1000)
    val date= Date(timestamp.time)
    val pattern="HH:mm:ss"
    val sdf=SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone= TimeZone.getDefault()
    return sdf.format(date)

}
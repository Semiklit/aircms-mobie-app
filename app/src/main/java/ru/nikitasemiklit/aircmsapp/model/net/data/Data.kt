package ru.nikitasemiklit.aircmsapp.model.net.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("device_id") val deviceId: Long,
    @SerializedName("ds18b20_temperature") val temp: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Long,
    @SerializedName("sds_p1") val p1: Int,
    @SerializedName("sds_p2") val p2: Int,
    @SerializedName("ts") val ts: Int,
    @SerializedName("wind_direction") val windDirection: String,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("iaq_tvoc") val tvoc: Int,
    @SerializedName("rad") val rad: Int
)
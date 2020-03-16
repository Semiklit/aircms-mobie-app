package ru.nikitasemiklit.aircmsapp.net

import com.google.gson.annotations.SerializedName
data class Device(
    @SerializedName ("id") val id: Long,
    @SerializedName ("lat") val lat: Double,
    @SerializedName ("lon") val lon: Double,
    @SerializedName ("address") val address : String
)
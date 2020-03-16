package ru.nikitasemiklit.aircmsapp.net

import com.google.gson.annotations.SerializedName

data class DevicesResponse (
    @SerializedName("data") val devices: List<Device>
)
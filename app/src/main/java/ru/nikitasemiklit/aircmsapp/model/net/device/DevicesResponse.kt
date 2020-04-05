package ru.nikitasemiklit.aircmsapp.model.net.device

import com.google.gson.annotations.SerializedName

data class DevicesResponse (
    @SerializedName("data") val devices: List<Device>
)
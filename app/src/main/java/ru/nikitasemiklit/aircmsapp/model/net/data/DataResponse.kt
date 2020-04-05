package ru.nikitasemiklit.aircmsapp.model.net.data

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("data") val data: List<Data>
)

package ru.nikitasemiklit.aircmsapp.model.net

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikitasemiklit.aircmsapp.model.net.data.DataResponse
import ru.nikitasemiklit.aircmsapp.model.net.device.DevicesResponse

interface AirCmsApi {

    @GET("/php/guiapi.php?devices")
    suspend fun getDevicesKtx(): Response<DevicesResponse>

    @GET("/php/guiapi.php")
    suspend fun getDataKtx(@Query("T") time: Int): Response<DataResponse>
}
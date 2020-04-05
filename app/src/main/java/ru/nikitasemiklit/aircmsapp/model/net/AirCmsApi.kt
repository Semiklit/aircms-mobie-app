package ru.nikitasemiklit.aircmsapp.model.net

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikitasemiklit.aircmsapp.model.net.data.DataResponse
import ru.nikitasemiklit.aircmsapp.model.net.device.DevicesResponse

interface AirCmsApi {

    @GET("/php/guiapi.php?devices")
    fun getDevices(): Single<Response<DevicesResponse>>

    @GET("/php/guiapi.php")
    fun getData(@Query("T") time: Int): Single<Response<DataResponse>>

}
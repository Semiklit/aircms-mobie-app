package ru.nikitasemiklit.aircmsapp.net

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirCmsApi {

    @GET("/php/guiapi.php?devices")
    fun getDevices(): Single<Response<DevicesResponse>>

    @GET("/php/guiapi.php")
    fun getDevices(@Query("T") time: Int): Deferred<DataResponse>

}
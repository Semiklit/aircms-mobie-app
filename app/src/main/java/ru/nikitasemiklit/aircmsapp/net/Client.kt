package ru.nikitasemiklit.aircmsapp.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    private val client = Retrofit.Builder()
        .baseUrl("https://aircms.online/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AirCmsApi::class.java)

    fun getDevices(): Single<Response<DevicesResponse>> {
        return client.getDevices();
    }

}
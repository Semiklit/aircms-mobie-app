package ru.nikitasemiklit.aircmsapp

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi

class ApiTest {
    @Test
    fun getTestDevices() {
        val client = Retrofit.Builder()
            .baseUrl("https://aircms.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AirCmsApi::class.java)
        val devices = client.getDevices().blockingGet().body()
        Assert.assertTrue(devices?.devices?.isNotEmpty()!!)
    }

    @Test
    fun getTestData() {
        val client = Retrofit.Builder()
            .baseUrl("https://aircms.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AirCmsApi::class.java)
        val data = client.getData(0).blockingGet().body()
        Assert.assertTrue(data?.data?.isNotEmpty()!!)
    }
}

package ru.nikitasemiklit.aircmsapp

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi

class ApiTest {
    @Test
    fun getTestDevices() {
        runBlocking {
            val client = Retrofit.Builder()
                .baseUrl("https://aircms.online/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AirCmsApi::class.java)

            val devices = client.getDevicesKtx().body()
            Assert.assertTrue(devices?.devices?.isNotEmpty()!!)
        }
    }

    @Test
    fun getTestData() {
        runBlocking {
            val client = Retrofit.Builder()
                .baseUrl("https://aircms.online/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AirCmsApi::class.java)
            val data = client.getDataKtx(0).body()
            Assert.assertTrue(data?.data?.isNotEmpty()!!)
        }
    }
}

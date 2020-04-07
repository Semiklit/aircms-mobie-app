package ru.nikitasemiklit.aircmsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitasemiklit.aircmsapp.model.database.*
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi
import java.io.IOException
import java.util.*
import kotlin.collections.HashSet

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var deviceDao: DeviceDao
    private lateinit var dataDao: DataDao
    private lateinit var db: CmsDatabase
    private lateinit var client: AirCmsApi

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CmsDatabase::class.java
        ).build()
        deviceDao = db.deviceDao()
        dataDao = db.dataDao()
        client = Retrofit.Builder()
            .baseUrl("https://aircms.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AirCmsApi::class.java)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        deviceDao.insert(
            arrayOf(
                DeviceEntity(1, 12.0, 12.0, "Челябниск"),
                DeviceEntity(2, 1.0, 1.0, "Магнитогорск")
            )
        )
        val selectedDevices = deviceDao.getDevices(11.5, 12.1, 11.5, 12.1)
        assert(selectedDevices.size == 1)
        assert(selectedDevices[0].id.compareTo(1) == 0)
    }

    @Test
    fun getDevicesAndSave() {
        val devices = client.getDevices().blockingGet().body()!!.devices.map { device ->
            DeviceEntity(
                device.id,
                device.lat,
                device.lon,
                device.address
            )
        }
        deviceDao.insert(devices.toTypedArray())
        val selectedDevices = deviceDao.getDevices(0.0, 0.0, 1000.0, 1000.0)
        assert(devices.size == selectedDevices.size)
    }

    @Test
    fun getDataAndSave() {
        val currentTime = Date().time
        val dataReponse = client.getData(0).blockingGet().body()!!.data
        val datas = dataReponse.map { data ->
            DataEntity(
                data.deviceId,
                currentTime,
                data.temp,
                data.humidity,
                data.pressure,
                data.p1,
                data.p2,
                data.ts,
                data.windDirection,
                data.windSpeed,
                data.tvoc,
                data.rad
            )
        }
        dataDao.addData(datas.toTypedArray())
        val deviceIds = HashSet<Long>(dataReponse.map { data -> data.deviceId })
        val selectedData = dataDao.getData(deviceIds.toTypedArray())
        assert(datas.size == selectedData.size)
    }
}
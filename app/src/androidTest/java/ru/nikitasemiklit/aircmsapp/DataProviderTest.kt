package ru.nikitasemiklit.aircmsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitasemiklit.aircmsapp.model.database.CmsDatabase
import ru.nikitasemiklit.aircmsapp.model.model.CMSDataProvider
import ru.nikitasemiklit.aircmsapp.model.model.CoordinatesInterval
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DataProviderTest {
    private lateinit var dataProvider: CMSDataProvider

    @Before
    fun createDataProvider() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room
            .inMemoryDatabaseBuilder(context, CmsDatabase::class.java)
            .build()
        val client = Retrofit
            .Builder()
            .baseUrl("https://aircms.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirCmsApi::class.java)
        dataProvider = CMSDataProvider(db, client)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        dataProvider.quite()
    }

    @Test
    fun loadAndGetData() {
        runBlocking {
            dataProvider.updateDeviceListKtx()
            dataProvider.loadLatestDataKtx()
            val listData =
                dataProvider.getDevicesByCoordinatesKtx(CoordinatesInterval(.0, 200.0, .0, 200.0))
            assertNotNull(listData)
            assertEquals(listData[0].first.id, listData[0].second.deviceId)
        }
    }
}
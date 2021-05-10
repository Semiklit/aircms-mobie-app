package ru.nikitasemiklit.aircmsapp.model.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nikitasemiklit.aircmsapp.model.database.CmsDatabase
import ru.nikitasemiklit.aircmsapp.model.database.DataEntity
import ru.nikitasemiklit.aircmsapp.model.database.DeviceEntity
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi
import java.util.*
import javax.inject.Inject

class CMSDataProvider @Inject constructor(val database: CmsDatabase, val client: AirCmsApi) {

    suspend fun getDevicesByCoordinatesKtx(c: CoordinatesInterval): List<Pair<DeviceEntity, DataEntity>> =
        withContext(Dispatchers.IO) {
            val devices = database.deviceDao().getDevices(c.latFrom, c.latTo, c.lonFrom, c.lonTo)
                .apply { sortBy { deviceEntity -> deviceEntity.id } }
            val data =
                database.dataDao().getData(devices.map { device -> device.id }.toTypedArray())
                    .apply { sortBy { dataEntity -> dataEntity.deviceId } }
            return@withContext data.map { dataEntity ->
                val id = dataEntity.deviceId
                val device = devices.last { deviceEntity -> deviceEntity.id.equals(id) }
                Pair(device, dataEntity)
            }
        }

    suspend fun loadLatestDataKtx() {
        withContext(Dispatchers.IO) {
            client.getDataKtx(0).also {
                if (it.isSuccessful) {
                    val dataDao = database.dataDao()
                    val currentTime = Date().time
                    val dataResponse = it.body()!!.data
                    val data = dataResponse.map { data ->
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
                    dataDao.addData(data.toTypedArray())
                }
            }
        }
    }

    suspend fun updateDeviceListKtx() {
        withContext(Dispatchers.IO) {
            client.getDevicesKtx().also {
                if (it.isSuccessful) {
                    val deviceDao = database.deviceDao()
                    val devices = it.body()!!.devices.map { device ->
                        DeviceEntity(
                            device.id,
                            device.lat,
                            device.lon,
                            device.address
                        )
                    }
                    deviceDao.insert(devices.toTypedArray())
                }
            }
        }
    }

    fun quite() {
        database.close()
    }
}
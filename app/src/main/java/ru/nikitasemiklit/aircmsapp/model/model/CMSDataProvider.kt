package ru.nikitasemiklit.aircmsapp.model.model

import io.reactivex.Completable
import io.reactivex.Single
import ru.nikitasemiklit.aircmsapp.model.database.CmsDatabase
import ru.nikitasemiklit.aircmsapp.model.database.DataEntity
import ru.nikitasemiklit.aircmsapp.model.database.DeviceEntity
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi
import java.util.*
import javax.inject.Inject

class CMSDataProvider @Inject constructor(val database: CmsDatabase, val client: AirCmsApi) {

    fun getDevicesByCoordinates(c: CoordinatesInterval): Single<List<Pair<DeviceEntity, DataEntity>>> {
        return Single.create { emitter ->
            val deviceDao = database.deviceDao()
            val devices = deviceDao.getDevices(c.latFrom, c.latTo, c.lonFrom, c.lonTo)
            devices.sortBy { deviceEntity -> deviceEntity.id }
            val dataDao = database.dataDao()
            val data = dataDao.getData(devices.map { device -> device.id }.toTypedArray())
            data.sortBy { dataEntity -> dataEntity.deviceId }
            emitter.onSuccess(data.map { dataEntity ->
                val id = dataEntity.deviceId
                val device = devices.last { deviceEntity -> deviceEntity.id.equals(id) }
                Pair(device, dataEntity)
            })
        }
    }

    fun loadLatestData(): Completable {
        return Completable.create { emitter ->
            client.getData(0).subscribe(
                { t ->
                    if (t.isSuccessful) {
                        val dataDao = database.dataDao()
                        val currentTime = Date().time
                        val dataResponse = t.body()!!.data
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
                        emitter.onComplete()
                    }
                },
                { error ->
                    emitter.tryOnError(error)
                }
            )
        }
    }

    fun updateDeviceList(): Completable {
        return Completable.create { emitter ->
            client.getDevices().subscribe(
                { t ->
                    if (t.isSuccessful) {
                        val deviceDao = database.deviceDao()
                        val devices = t.body()!!.devices.map { device ->
                            DeviceEntity(
                                device.id,
                                device.lat,
                                device.lon,
                                device.address
                            )
                        }
                        deviceDao.insert(devices.toTypedArray())
                        emitter.onComplete()
                    }
                },
                { error ->
                    emitter.tryOnError(error)
                }
            )
        }
    }

    fun quite() {
        database.close()
    }
}
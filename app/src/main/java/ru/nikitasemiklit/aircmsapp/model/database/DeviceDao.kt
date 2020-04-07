package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeviceDao {

    @Query("SELECT * FROM DeviceEntity WHERE lat >= :latFrom AND lat<= :latTo AND lon >= :lonFrom AND lon <= :lonTo")
    fun getDevices(
        latFrom: Double,
        latTo: Double,
        lonFrom: Double,
        lonTo: Double
    ): Array<DeviceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(devices: Array<DeviceEntity>)
}
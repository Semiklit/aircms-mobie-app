package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM DataEntity WHERE deviceId IN (:devices)")
    fun getData(devices: Array<DeviceEntity>): Array<DataEntity>

    @Query("DELETE FROM DataEntity WHERE time < :t")
    fun clearData(t: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(data: Array<DataEntity>)
}
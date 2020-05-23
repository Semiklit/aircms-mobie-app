package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM DataEntity WHERE deviceId IN (:deviceIds) GROUP BY deviceId")
    fun getData(deviceIds: Array<Long>): Array<DataEntity>

    @Query("SELECT * FROM DataEntity WHERE deviceId = :deviceId ORDER BY time DESC")
    fun getDataForDevice(deviceId: Long): Array<DataEntity>

    @Query("SELECT time FROM DataEntity ORDER BY time DESC")
    fun getTimeSet(): Array<Long>

    @Query("DELETE FROM DataEntity WHERE time < :t")
    fun clearData(t: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(data: Array<DataEntity>)
}
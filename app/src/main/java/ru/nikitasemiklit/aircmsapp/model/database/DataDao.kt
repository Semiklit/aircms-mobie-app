package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM DataEntity WHERE deviceId IN (:deviceIds)")
    fun getData(deviceIds: Array<Long>): Array<DataEntity>

    @Query("DELETE FROM DataEntity WHERE time < :t")
    fun clearData(t: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(data: Array<DataEntity>)
}
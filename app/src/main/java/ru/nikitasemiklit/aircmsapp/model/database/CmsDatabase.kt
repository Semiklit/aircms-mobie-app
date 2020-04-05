package ru.nikitasemiklit.aircmsapp.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase

@Database(entities = [DataDao::class, DeviceDao::class], version = 1, exportSchema = false)
abstract class CmsDatabase() : RoomDatabase() {
    abstract fun dataDao(): DeviceDao
    abstract fun deviceDao(): DeviceDao
}

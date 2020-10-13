package ru.nikitasemiklit.aircmsapp.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class, DeviceEntity::class], version = 1, exportSchema = false)
abstract class CmsDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
    abstract fun deviceDao(): DeviceDao
}

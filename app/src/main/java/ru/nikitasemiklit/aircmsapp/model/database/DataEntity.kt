package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Entity

@Entity(primaryKeys = ["deviceId", "time"])
data class DataEntity(
    val deviceId: Long,
    val time: Long,
    val temp: Double,
    val humidity: Int,
    val pressure: Long,
    val p1: Int,
    val p2: Int,
    val ts: Int,
    val windDirection: String?,
    val windSpeed: Double,
    val tvoc: Int,
    val rad: Int
)
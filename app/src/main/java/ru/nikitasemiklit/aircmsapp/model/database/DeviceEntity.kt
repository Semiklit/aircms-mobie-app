package ru.nikitasemiklit.aircmsapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceEntity(
    @PrimaryKey val id: Long,
    val lat: Double,
    val lon: Double,
    val address: String
)
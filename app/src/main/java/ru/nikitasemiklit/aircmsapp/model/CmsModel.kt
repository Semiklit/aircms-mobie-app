package ru.nikitasemiklit.aircmsapp.model

import ru.nikitasemiklit.aircmsapp.model.database.CmsDatabase
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi

class CmsModel(val client: AirCmsApi, val database: CmsDatabase) {


    fun updateDatabase() {
        client.getDevices().subscribe()
    }
}
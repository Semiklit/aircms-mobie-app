package ru.nikitasemiklit.aircmsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.nikitasemiklit.aircmsapp.di.DaggerAppComponent
import ru.nikitasemiklit.aircmsapp.model.database.DataEntity
import ru.nikitasemiklit.aircmsapp.model.database.DeviceEntity
import ru.nikitasemiklit.aircmsapp.model.model.CMSDataProvider
import ru.nikitasemiklit.aircmsapp.model.model.CoordinatesInterval
import javax.inject.Inject

class MapActivityViewModel(app: Application) : AndroidViewModel(app) {

    private val currentCoordinates = MutableLiveData<CoordinatesInterval>()

    val devices = MutableLiveData<List<Pair<DeviceEntity, DataEntity>>>()

    init {
        DaggerAppComponent.builder().application(app).build().inject(this)
        viewModelScope.launch {
            update()
        }
    }

    private suspend fun update() {
        val futureData = viewModelScope.async { dataProvider.loadLatestDataKtx() }
        val futureDevice = viewModelScope.async { dataProvider.updateDeviceListKtx() }
        futureData.await()
        futureDevice.await()
    }

    @Inject
    lateinit var dataProvider: CMSDataProvider

    fun onMapMoved(coordinates: CoordinatesInterval) {
        currentCoordinates.postValue(coordinates)
        viewModelScope.launch {
            dataProvider.getDevicesByCoordinatesKtx(coordinates).also {
                devices.postValue(it)
            }
        }
    }

    override fun onCleared() {
        dataProvider.quite()
        super.onCleared()
    }

}
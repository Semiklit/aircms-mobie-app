package ru.nikitasemiklit.aircmsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.nikitasemiklit.aircmsapp.di.DaggerAppComponent
import ru.nikitasemiklit.aircmsapp.model.database.DataEntity
import ru.nikitasemiklit.aircmsapp.model.database.DeviceEntity
import ru.nikitasemiklit.aircmsapp.model.model.CMSDataProvider
import ru.nikitasemiklit.aircmsapp.model.model.CoordinatesInterval
import javax.inject.Inject

class MapActivityViewModel(app: Application) : AndroidViewModel(app) {

    private val subscriptions = CompositeDisposable()
    private val currentCoordinates = MutableLiveData<CoordinatesInterval>()

    val devices = MutableLiveData<List<Pair<DeviceEntity, DataEntity>>>()
        get

    init {
        val appComponent = DaggerAppComponent.builder().application(getApplication()).build()
        appComponent.inject(this)
        subscriptions.add(
            dataProvider.loadLatestData().concatWith(dataProvider.updateDeviceList()).subscribe {
                currentCoordinates.observeForever { c ->
                    dataProvider.getDevicesByCoordinates(c).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
                            devices.postValue(result)
                        }
                }
            })
    }

    @Inject
    lateinit var dataProvider: CMSDataProvider

    fun onMapMoved(coordinates: CoordinatesInterval) {
        currentCoordinates.postValue(coordinates)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}
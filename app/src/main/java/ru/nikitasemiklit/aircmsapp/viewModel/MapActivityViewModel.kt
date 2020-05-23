package ru.nikitasemiklit.aircmsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.nikitasemiklit.aircmsapp.di.DaggerAppComponent
import ru.nikitasemiklit.aircmsapp.model.model.CMSDataProvider
import javax.inject.Inject

class MapActivityViewModel(app: Application) : AndroidViewModel(app) {

    init {
        DaggerAppComponent.create().inject(app)
    }

    @Inject
    lateinit var dataProvider: CMSDataProvider


}
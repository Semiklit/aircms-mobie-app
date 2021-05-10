package ru.nikitasemiklit.aircmsapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nikitasemiklit.aircmsapp.di.modules.DataModule
import ru.nikitasemiklit.aircmsapp.view.MapsActivity
import ru.nikitasemiklit.aircmsapp.viewModel.MapActivityViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(viewModel: MapActivityViewModel)
    fun inject(activity: MapsActivity)
}
package ru.nikitasemiklit.aircmsapp.di

import android.content.Context
import dagger.Component
import ru.nikitasemiklit.aircmsapp.di.modules.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(context: Context)
}
package ru.nikitasemiklit.aircmsapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun injectApp(app: Application): Context {
        return app.applicationContext
    }
}
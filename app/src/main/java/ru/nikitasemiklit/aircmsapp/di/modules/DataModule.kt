package ru.nikitasemiklit.aircmsapp.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitasemiklit.aircmsapp.model.database.CmsDatabase
import ru.nikitasemiklit.aircmsapp.model.model.CMSDataProvider
import ru.nikitasemiklit.aircmsapp.model.net.AirCmsApi
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Singleton
    abstract fun cmsDataModule(): CMSDataProvider

    companion object {
        @Provides
        @Singleton
        fun AirCmsApi() = Retrofit.Builder()
            .baseUrl("https://aircms.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(AirCmsApi::class.java)

        @Provides
        fun dataBase(context: Context) = Room
            .databaseBuilder(context, CmsDatabase::class.java, "CMSDatabase")
            .build()
    }
}
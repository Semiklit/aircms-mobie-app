package ru.nikitasemiklit.aircmsapp.di.modules

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AirCmsApi::class.java)

        @Provides
        fun dataBase(context: Context) = Room
            .databaseBuilder(context, CmsDatabase::class.java, "CMSDatabase")
            .build()
    }
}
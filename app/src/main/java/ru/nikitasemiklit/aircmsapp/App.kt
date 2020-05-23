package ru.nikitasemiklit.aircmsapp

import android.app.Application


class App : Application() {
//    @Inject
//    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity?>? = null

    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent
//            .builder()
//            .application(this)
//            .build()
//            .inject(this)
    }

//    fun activityInjector(): AndroidInjector<Activity?>? {
//        return dispatchingAndroidInjector
//    }
}

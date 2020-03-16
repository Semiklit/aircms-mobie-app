package ru.nikitasemiklit.aircmsapp

import androidx.annotation.MainThread
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*
import ru.nikitasemiklit.aircmsapp.net.Client

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {
    @Test
    fun getTestDevices() {
        val devices = Client().getDevices().blockingGet().body()
        assert(devices?.devices?.isNotEmpty()!!)
    }


}

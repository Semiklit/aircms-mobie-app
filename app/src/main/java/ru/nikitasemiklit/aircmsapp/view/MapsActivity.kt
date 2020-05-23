package ru.nikitasemiklit.aircmsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.nikitasemiklit.aircmsapp.R
import ru.nikitasemiklit.aircmsapp.model.model.CoordinatesInterval
import ru.nikitasemiklit.aircmsapp.viewModel.MapActivityViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: MapActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MapActivityViewModel::class.java)
    }

    override fun onMapReady(map: GoogleMap) {
        map.setOnCameraIdleListener {
            viewModel.onMapMoved(
                CoordinatesInterval(
                    map.projection.visibleRegion.nearRight.latitude,
                    map.projection.visibleRegion.farLeft.latitude,
                    map.projection.visibleRegion.farLeft.longitude,
                    map.projection.visibleRegion.nearRight.longitude
                )
            )
        }
        viewModel.devices.observe(this, Observer { devicesAndData ->
            devicesAndData.forEach { pair ->
                val markerOptions = MarkerOptions()
                    .position(
                        LatLng(pair.first.lat, pair.first.lon)
                    )
                map.addMarker(markerOptions)
            }
        })
    }
}

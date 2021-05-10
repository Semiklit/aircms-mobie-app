package ru.nikitasemiklit.aircmsapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitasemiklit.aircmsapp.R
import ru.nikitasemiklit.aircmsapp.databinding.ActivityMapsBinding
import ru.nikitasemiklit.aircmsapp.model.model.CoordinatesInterval
import ru.nikitasemiklit.aircmsapp.viewModel.MapActivityViewModel

class MapsActivity : AppCompatActivity() {

    private lateinit var viewModel: MapActivityViewModel
    private lateinit var binding: ActivityMapsBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
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
            map.setOnMapClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MapActivityViewModel::class.java)

    }
}
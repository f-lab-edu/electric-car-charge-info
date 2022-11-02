package com.example.ecarchargeinfo.main.presentation.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityMainBinding
import com.example.ecarchargeinfo.main.presentation.viewmodel.MainViewModel
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import com.example.ecarchargeinfo.main.presentation.helper.PermissionHelper
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val mapFragment = MapFragment()

    companion object {
        const val LOCATION_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeUIState()
        binding.inputs = viewModel.inputs
        binding.lifecycleOwner = this

        PermissionHelper.checkPermission(this@MainActivity)

        supportFragmentManager.beginTransaction().replace(R.id.main_map, mapFragment, "MAP")
            .commit()

        binding.btn1.setOnClickListener{
            mapFragment.initMap()
        }

    }

    private fun observeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.outputs.searchFilterState.collect {
                    if (it is MainSearchFilterState.Main) {
                        binding.searchFilterEntity = it.searchFilters
                    }
                }
            }
        }
    }

    /*
      private fun getLatLng(): Location? {
          var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
          if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
              PackageManager.PERMISSION_GRANTED &&
              ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
              PackageManager.PERMISSION_GRANTED
          ) {
              var currentLatLng =
                  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?:
                  locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
              return currentLatLng
          }
          return null
      }*/


    /*override fun onMapReady(p0: GoogleMap) {
        if (userLocation != null) {
            mMap = p0
            val nowLocation = LatLng(userLocation!!.latitude, userLocation!!.longitude)
            mMap.addMarker(MarkerOptions().position(nowLocation).title("현 위치"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowLocation, 15f))
        }
    }*/
}

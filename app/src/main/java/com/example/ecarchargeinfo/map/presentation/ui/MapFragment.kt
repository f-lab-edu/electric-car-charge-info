package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentMapBinding
import com.example.ecarchargeinfo.info.presentation.ui.InfoActivity
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainLocationState
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity
import com.example.ecarchargeinfo.main.presentation.viewmodel.MainViewModel
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.presentation.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var mainActivity: MainActivity? = null
    @Inject
    lateinit var mapViewModel: MapViewModel
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
        //      viewmodel2 = ViewModelProvider(activity as ViewModelStoreOwner)[MapViewModel::class.java]
        gMap = binding.mapview
        gMap.onCreate(savedInstanceState)
        gMap.onResume()
        gMap.getMapAsync(this)
        return binding.root
    }

    fun initMap() {
        gMap.getMapAsync(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (activity as MainActivity)
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        gMap.onSaveInstanceState(outState)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        observeUIState(mMap)
        observeLocation(mMap)
        mapViewModel.getGeocoder("126.9784147, 37.5666805")
//        val location: LatLng = viewModel.getLatLng(context as MainActivity)

        /* NewAreaMarker(location)
         var marker = MarkerOptions().position(location).title("현 위치")
         mMap.addMarker(marker)?.showInfoWindow()
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, MapConstants.DEFAULT_ZOOM))
         mMap.setOnMarkerClickListener(this)
         mMap.setOnCameraIdleListener {
             val centerLocation = googleMap.projection.visibleRegion.latLngBounds.center
             NewAreaMarker(centerLocation)
         }*/
        /*mMap.isMyLocationEnabled
        mMap.uiSettings.isMyLocationButtonEnabled*/

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(activity, InfoActivity::class.java)
        intent.apply {
            this.putExtra("address", marker.title)
        }
        startActivity(intent)
        return true
    }

    private fun observeUIState(googleMap: GoogleMap) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.outputs.chargerInfoState.collect() {
                    if (it is MainChargerInfoState.Main) {
                        it.chargerInfo.forEach { data ->
                            val location = LatLng(data.lat.toDouble(), data.longi.toDouble())
                            var marker = MarkerOptions()
                                .position(location)
                                .title(data.csNm)
                                .snippet(data.addr)
                            googleMap.addMarker(marker)
                        }
                    }
                }
            }
        }
    }

    private fun observeLocation(googleMap: GoogleMap) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.locationState.collect() {
                    if (it is MainLocationState.Main) {
                        var marker =
                            MarkerOptions().position(it.locationInfo.coordinate).title("현 위치")
                        googleMap.addMarker(marker)?.showInfoWindow()
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                it.locationInfo.coordinate,
                                MapConstants.DEFAULT_ZOOM
                            )
                        )

                    }
                }
            }
        }
    }

    fun NewAreaMarker(location: LatLng) {
        Thread {
            try {
                val krAddress = viewModel.test(
                    resources.getString(R.string.naver_client_id),
                    resources.getString(R.string.naver_client_key),
                    (location.longitude.toString() + "," + location.latitude.toString())
                )
                viewModel.getApiAll(krAddress)
            } catch (e: Exception) {

            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        gMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        gMap.onStop()
    }

    override fun onResume() {
        super.onResume()
        gMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        gMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        gMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        gMap.onLowMemory()
    }

}

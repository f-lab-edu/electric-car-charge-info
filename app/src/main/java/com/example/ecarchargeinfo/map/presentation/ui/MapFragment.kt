package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentMapBinding
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.ui.InfoActivity
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity
import com.example.ecarchargeinfo.main.presentation.viewmodel.MainViewModel
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.util.*
import java.util.stream.Collectors.toList


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var mainActivity: MainActivity? = null
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        observeUIState(mMap)
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var currentLatLng =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val location = LatLng(currentLatLng!!.latitude, currentLatLng!!.longitude)

        val location2 = viewModel.inputs.getLatLng(context as MainActivity)

        val geocoder = Geocoder(context as MainActivity, Locale.KOREA)
        val address =
            geocoder.getFromLocation(currentLatLng!!.latitude, currentLatLng!!.longitude, 3)

        address.let {
            val needAddress =
                (address!!.get(0).adminArea.toString() + " " + address!!.get(0).subLocality.toString())
            viewModel.getApiAll(needAddress)
        }

        var marker = MarkerOptions().position(location).title("현 위치")
        mMap.addMarker(marker)!!.showInfoWindow()
        //googleMap.addMarker(MarkerOptions().position(location).title("현 위치"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, MapConstants.DEFAULT_ZOOM))
        mMap.setOnMarkerClickListener(this)


        mMap.setOnCameraIdleListener {
            val centerLocation = googleMap.projection.visibleRegion.latLngBounds.center
            println("@@" + centerLocation)
            println("@@" + centerLocation.latitude + " " + centerLocation.longitude)
            val addressTest =
                geocoder.getFromLocation(centerLocation.latitude, centerLocation.longitude, 1)
            println("@@"+addressTest)
            val needAddressTest =
                (addressTest!!.get(0).adminArea.toString() + " " + addressTest!!.get(0).subLocality.toString())
            println("@@" + needAddressTest)
            viewModel.getApiAll(needAddressTest)
            observeUIState(mMap)
        }

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
                            var test = MarkerOptions()
                                .position(location)
                                .title(data.addr)
                            googleMap.addMarker(test)
                        }
                    }
                }
            }
        }
    }


}

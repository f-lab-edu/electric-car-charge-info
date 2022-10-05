package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentMapBinding
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


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
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

    fun initMap()   {
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
    override fun onMapReady(p0: GoogleMap) {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var currentLatLng =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?:
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val location = LatLng(currentLatLng!!.latitude, currentLatLng!!.longitude)
        //val location = LatLng(MapConstants.DEFAULT_LOCATION[0], MapConstants.DEFAULT_LOCATION[1])
        p0.addMarker(MarkerOptions().position(location).title("현 위치"))
        p0.moveCamera(CameraUpdateFactory.newLatLngZoom(location ,MapConstants.DEFAULT_ZOOM))
        p0.setOnMarkerClickListener(this)
        viewModel.initSearchFilter()
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        // TODO
//        mainActivity!!.viewModel.changeViewInfoState()

        return true
    }


}
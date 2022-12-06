package com.example.ecarchargeinfo.info.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentInfoMapBinding
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class InfoMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentInfoMapBinding
    private lateinit var mMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_map, container, false)
        gMap = binding.infoMapView
        gMap.onCreate(savedInstanceState)
        gMap.getMapAsync(this)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val lat = arguments?.getString("lat")
        val lon = arguments?.getString("lon")
        if (lat != null && lon != null) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lat.toDouble(),
                        lon.toDouble()
                    ), MapConstants.MARKER_ZOOM + 1f
                )
            )
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat.toDouble(), lon.toDouble()))
                    .title("충전소")
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            Bitmap.createScaledBitmap(
                                (ResourcesCompat.getDrawable(
                                    requireContext().resources,
                                    R.drawable.volt, null
                                ) as BitmapDrawable).bitmap,
                                120, 120, false
                            )
                        )
                    )

            )?.showInfoWindow()
            mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(
                            LatLng(
                                lat.toDouble(),
                                lon.toDouble()
                            )
                        ) // Sets the center of the map to Mountain View
                        .zoom(17f)            // Sets the zoom
                        .bearing(0f)         // Sets the orientation of the camera to east
                        .tilt(30f)            // Sets the tilt of the camera to 30 degrees
                        .build()
                )
            )
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        gMap.onSaveInstanceState(outState)
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

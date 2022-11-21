package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import javax.inject.Inject

class MyClusterManager<T> @Inject constructor(
    private val context: Context,
    private val mMap: GoogleMap,
    private val fragment: MapFragment
) :
    ClusterManager<MyItem>(context, mMap) {
    private lateinit var location: LatLng

    override fun onCameraIdle() {
        super.onCameraIdle()
        location = mMap.projection.visibleRegion.latLngBounds.center
        fragment.mapViewModel.updateGeocoding(
            location.longitude.toString() + "," + location.latitude.toString()
        )
    }


}

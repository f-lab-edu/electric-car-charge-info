package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MyItem(
    private val _position: LatLng,
    private val _title: String,
    private val _icon: BitmapDescriptor
) : ClusterItem {


    override fun getPosition(): LatLng = _position

    override fun getTitle(): String = _title

    override fun getSnippet(): String = ""

    fun getIcon(): BitmapDescriptor = _icon
}


class ClusterRenderer(
    context: Context, map: GoogleMap, clusterManager: MyClusterManager<MyItem>
)   : DefaultClusterRenderer<MyItem>(context, map, clusterManager)  {
    init {
        clusterManager.renderer = this
    }

    override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
        markerOptions.icon(item.getIcon())
        markerOptions.visible(true)
    }
}

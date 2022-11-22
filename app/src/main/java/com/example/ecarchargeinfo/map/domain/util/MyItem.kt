package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MyItem(
    private val _position: LatLng,
    private val _title: String,
    private val _snippet: String,
    private val _icon: BitmapDescriptor,
    private val _addr: String,
    private val _chargeTp: String,
    private val _cpTp: String

) : ClusterItem {

    override fun getPosition(): LatLng = _position

    override fun getTitle(): String = _title

    override fun getSnippet(): String =
        when (_snippet) {
            "1" -> "충전가능"
            "2" -> "충전중"
            "3" -> "고장/점검"
            "4" -> "통신장애"
            "5" -> "통신미연결"
            else -> "상태 조회 불가"
        }

    fun getIcon(): BitmapDescriptor = _icon
    fun getAddr(): String = _addr
    fun getChargeTp(): String = _chargeTp
    fun getCptp(): String = _cpTp
}


class ClusterRenderer(
    context: Context, map: GoogleMap, clusterManager: MyClusterManager<MyItem>
) : DefaultClusterRenderer<MyItem>(context, map, clusterManager) {
    init {
        clusterManager.renderer = this

    }

    override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
        markerOptions.icon(item.getIcon())
        markerOptions.snippet(item.snippet)
        markerOptions.title(item.title)
        markerOptions.visible(true)

        super.onBeforeClusterItemRendered(item, markerOptions)
    }
}

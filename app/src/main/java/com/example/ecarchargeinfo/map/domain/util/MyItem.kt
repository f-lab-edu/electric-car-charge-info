package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_EMPTY
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
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
            CHARGER_STAT_OK -> CHARGER_STAT_OK_VALUE
            CHARGER_STAT_ON_UES -> CHARGER_STAT_ON_UES_VALUE
            CHARGER_STAT_BREAK -> CHARGER_STAT_BREAK_VALUE
            CHARGER_STAT_NETWORK_ERROR -> CHARGER_STAT_NETWORK_ERROR_VALUE
            CHARGER_STAT_NETWORK_DISCONNECT -> CHARGER_STAT_NETWORK_DISCONNECT_VALUE
            else -> CHARGER_STAT_EMPTY
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

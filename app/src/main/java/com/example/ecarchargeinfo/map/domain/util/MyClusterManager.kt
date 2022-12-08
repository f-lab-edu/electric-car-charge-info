package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.presentation.viewmodel.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer


class MyClusterManager<T>(
    private val context: Context,
    private val mMap: GoogleMap,
    private val mapViewModel: MapViewModel
) : ClusterManager<MapCluster>(context, mMap) {

    private lateinit var centerLocation: LatLng
    private lateinit var location: LatLng

    init {
        renderer = ClusterRenderer(context, mMap)

        setOnClusterClickListener(ClusterManager.OnClusterClickListener<MapCluster> {
            mapViewModel.onMoveCamera(
                position = LatLng(it.position.latitude, it.position.longitude),
                zoom = mMap.cameraPosition.zoom + 1f
            )
            return@OnClusterClickListener false
        })

        setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<MapCluster> {
            mapViewModel.onMoveCamera(
                position = LatLng(it.position.latitude, it.position.longitude),
                zoom = MapConstants.MARKER_ZOOM
            )
            mapViewModel.onMarkerClick(
                visible = true,
                markerInfo = MarkerInfo(
                    cpNm = it.title.toString(),
                    addr = it.addr.toString(),
                    chargeTp = it.chargeTp.toString(),
                    cpStat = it.snippet.toString()
                )
            )
            return@OnClusterItemClickListener false
        })

    }

    override fun onCameraIdle() {
        super.onCameraIdle()
        centerLocation = mMap.projection.visibleRegion.latLngBounds.center
        mapViewModel.updateGeocoding(
            centerLocation.longitude.toString() + "," + centerLocation.latitude.toString()
        )
    }

    inner class ClusterRenderer(
        context: Context, map: GoogleMap
    ) : DefaultClusterRenderer<MapCluster>(context, map, this) {
        override fun onBeforeClusterItemRendered(item: MapCluster, markerOptions: MarkerOptions) {
            markerOptions.icon(item.icon)
            markerOptions.snippet(
                when (item.snippet) {
                    MapConstants.ChargerStat.CHARGER_STAT_OK -> MapConstants.ChargerStat.CHARGER_STAT_AVAILABLE
                    MapConstants.ChargerStat.CHARGER_STAT_ON_UES -> MapConstants.ChargerStat.CHARGER_STAT_CHARGING
                    MapConstants.ChargerStat.CHARGER_STAT_BREAK -> MapConstants.ChargerStat.CHARGER_STAT_FAULT_MAINT
                    MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR -> MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE
                    MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT -> MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE
                    else -> MapConstants.ChargerStat.CHARGER_STAT_EMPTY
                }
            )
            markerOptions.title(item.title)
            markerOptions.visible(true)

            super.onBeforeClusterItemRendered(item, markerOptions)
        }
    }
}

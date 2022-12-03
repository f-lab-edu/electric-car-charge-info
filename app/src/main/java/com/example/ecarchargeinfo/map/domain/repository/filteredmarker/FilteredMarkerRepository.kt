package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MapCluster

interface FilteredMarkerRepository {
    fun getFilteredMarkerArray(
        chargerMarkerList: List<MapCluster>,
        type: String
    ): List<MapCluster>
}

package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MapCluster
import javax.inject.Inject

class FilteredMarkerRepositoryImpl @Inject constructor() : FilteredMarkerRepository {
    override fun getFilteredMarkerArray(
        chargerMarkerList: List<MapCluster>,
        type: String
    ): List<MapCluster> =
        chargerMarkerList.filter {
            it.cpTp == type
        }
}

package com.example.ecarchargeinfo.map.domain.usecase.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MapCluster

interface IGetFilteredMarkerUseCase {
    operator fun invoke(chargerMarkerArray: List<MapCluster>, type: String): List<MapCluster>
}

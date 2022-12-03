package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem

interface FilteredMarkerRepository {
    fun getFilteredMarkerArray(
        chargerMarkerList: List<MyItem>,
        type: String
    ): List<MyItem>
}

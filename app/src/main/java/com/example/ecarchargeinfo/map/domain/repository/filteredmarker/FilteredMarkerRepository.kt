package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem

interface FilteredMarkerRepository {
    fun getFilteredMarkerArray(
        chargerMarkerArray: ArrayList<MyItem>,
        type: String
    ): ArrayList<MyItem>
}

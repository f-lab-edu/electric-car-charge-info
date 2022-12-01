package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem
import javax.inject.Inject

class FilteredMarkerRepositoryImpl @Inject constructor() : FilteredMarkerRepository {
    override fun getFilteredMarkerArray(
        chargerMarkerList: List<MyItem>,
        type: String
    ): List<MyItem> =
        chargerMarkerList.filter {
            it.getCptp() == type
        }
}

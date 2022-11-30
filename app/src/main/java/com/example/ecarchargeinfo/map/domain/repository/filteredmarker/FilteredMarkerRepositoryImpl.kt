package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem
import java.util.stream.Collectors
import javax.inject.Inject

class FilteredMarkerRepositoryImpl @Inject constructor() : FilteredMarkerRepository {
    override fun getFilteredMarkerArray(
        chargerMarkerArray: List<MyItem>,
        type: String
    ): List<MyItem> =
        chargerMarkerArray.filter {
            it.getCptp() == type
        }
}

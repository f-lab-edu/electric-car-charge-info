package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem
import java.util.stream.Collectors
import javax.inject.Inject

class FilteredMarkerRepositoryImpl @Inject constructor() : FilteredMarkerRepository {
    override fun getFilteredMarkerArray(
        chargerMarkerArray: ArrayList<MyItem>,
        type: String
    ): ArrayList<MyItem> =
        chargerMarkerArray.stream()
            .filter { it -> it.getCptp() == type }
            .collect(Collectors.toList()) as ArrayList<MyItem>

}

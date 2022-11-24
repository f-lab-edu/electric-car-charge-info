package com.example.ecarchargeinfo.map.domain.repository.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem
import javax.inject.Inject

class FilteredMarkerRepositoryImpl @Inject constructor() : FilteredMarkerRepository {
    override fun getFilteredMarkerArray(
        chargerMarkerArray: ArrayList<MyItem>,
        type: String
    ): ArrayList<MyItem> {
        val filteredArray = ArrayList<MyItem>()
        chargerMarkerArray.let {
            it.forEach {
                if (it.getCptp() == type)
                    filteredArray.add(it)
            }
        }
        return filteredArray
    }
}

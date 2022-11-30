package com.example.ecarchargeinfo.map.domain.usecase.filteredmarker

import com.example.ecarchargeinfo.map.domain.repository.filteredmarker.FilteredMarkerRepository
import com.example.ecarchargeinfo.map.domain.util.MyItem
import javax.inject.Inject

class GetFilteredMarkerUseCase @Inject constructor(
    private val filteredMarkerRepository: FilteredMarkerRepository
) : IGetFilteredMarkerUseCase {
    override fun invoke(chargerMarkerArray: ArrayList<MyItem>, type: String): ArrayList<MyItem> =
        filteredMarkerRepository.getFilteredMarkerArray(
            chargerMarkerArray = chargerMarkerArray,
            type = type
        )
}

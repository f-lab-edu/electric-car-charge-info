package com.example.ecarchargeinfo.map.domain.usecase.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem

interface IGetFilteredMarkerUseCase {
    operator fun invoke(chargerMarkerArray: List<MyItem>, type: String): List<MyItem>
}

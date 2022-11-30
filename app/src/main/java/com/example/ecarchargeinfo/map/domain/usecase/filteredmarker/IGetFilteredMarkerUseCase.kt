package com.example.ecarchargeinfo.map.domain.usecase.filteredmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem

interface IGetFilteredMarkerUseCase {
    operator fun invoke(chargerMarkerArray: ArrayList<MyItem>, type: String): ArrayList<MyItem>
}

package com.example.ecarchargeinfo.map.domain.usecase.allmarker

import com.example.ecarchargeinfo.map.domain.repository.allmarker.AllMarkerRepository
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import javax.inject.Inject

class GetAllMarkerUseCase @Inject constructor(
    private val allMarkerRepository: AllMarkerRepository
) : IGetAllMarkerUseCase {
    override fun invoke(list :List<ChargerInfo>): ArrayList<MyItem> =
        allMarkerRepository.getAllMarker(list)
}

package com.example.ecarchargeinfo.map.domain.usecase.chargerinfo

import com.example.ecarchargeinfo.map.domain.repository.chargerinfo.ChargerInfoRepository
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import javax.inject.Inject

class GetChargerInfoUseCase @Inject constructor(
    private val chargerInfoRepository: ChargerInfoRepository
) : IChargerInfoUseCase {
    override suspend fun invoke(cond: String): List<ChargerInfo> =
        chargerInfoRepository.getChargerInfo(cond)
}

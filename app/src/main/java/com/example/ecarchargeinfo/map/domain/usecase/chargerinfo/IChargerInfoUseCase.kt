package com.example.ecarchargeinfo.map.domain.usecase.chargerinfo

import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo

interface IChargerInfoUseCase {
    suspend operator fun invoke(cond: String): List<ChargerInfo>
}

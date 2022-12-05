package com.example.ecarchargeinfo.info.presentation.output

import com.example.ecarchargeinfo.info.domain.model.Charger
import com.example.ecarchargeinfo.main.presentation.output.MainEffect
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InfoOutPuts {
    val chargerInfoState: StateFlow<InfoChargerInfoState>
    val distanceEvent: SharedFlow<String>
    val chargersEvent: SharedFlow<List<Charger>>
    val infoEffect: SharedFlow<InfoEffect>
}

sealed class InfoEffect {
    data class CopyAddress(val address: String) : InfoEffect()
}

sealed class InfoChargerInfoState {
    object Initial : InfoChargerInfoState()
    data class Main(val chargerInfo: List<ChargerInfo>) : InfoChargerInfoState()
}

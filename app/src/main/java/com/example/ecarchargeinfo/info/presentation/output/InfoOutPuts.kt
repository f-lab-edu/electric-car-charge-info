package com.example.ecarchargeinfo.info.presentation.output

import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import kotlinx.coroutines.flow.StateFlow

interface InfoOutPuts {
    val chargerInfoState: StateFlow<InfoChargerInfoState>
}

sealed class InfoChargerInfoState {
    object Initial : InfoChargerInfoState()
    data class Main(val chargerInfo: List<ChargerInfo>) : InfoChargerInfoState()
}

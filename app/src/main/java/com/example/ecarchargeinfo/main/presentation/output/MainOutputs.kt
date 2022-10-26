package com.example.ecarchargeinfo.main.presentation.output

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.retrofit.model.ChargerInfo
import kotlinx.coroutines.flow.StateFlow

interface MainOutputs {
    val searchFilterState: StateFlow<MainSearchFilterState>
    val chargerInfoState: StateFlow<MainChargerInfoState>
}

sealed class MainSearchFilterState {
    object Initial : MainSearchFilterState()
    data class Main(val searchFilters: MainSearchFilterEntity) : MainSearchFilterState()
}

sealed class MainChargerInfoState {
    object Initial : MainChargerInfoState()
    data class Main(val chargerInfo: List<ChargerInfo>) : MainChargerInfoState()
}

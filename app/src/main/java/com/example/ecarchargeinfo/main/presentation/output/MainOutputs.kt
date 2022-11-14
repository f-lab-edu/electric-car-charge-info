package com.example.ecarchargeinfo.main.presentation.output

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.map.domain.model.LocationCoord
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import kotlinx.coroutines.flow.StateFlow

interface MainOutputs {
    val searchFilterState: StateFlow<MainSearchFilterState>
    val chargerInfoState: StateFlow<MainChargerInfoState>
    val locationState: StateFlow<MainLocationState>
}

sealed class MainSearchFilterState {
    object Initial : MainSearchFilterState()
    data class Main(val searchFilters: MainSearchFilterEntity) : MainSearchFilterState()
}

sealed class MainChargerInfoState {
    object Initial : MainChargerInfoState()
    data class Main(val chargerInfo: List<ChargerInfo>) : MainChargerInfoState()
}

sealed class MainLocationState  {
    object Initial: MainLocationState()
    data class Main(val locationInfo: LocationCoord) : MainLocationState()
}

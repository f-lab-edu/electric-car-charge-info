package com.example.ecarchargeinfo.main.presentation.output

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.map.domain.entity.ChargerDetailEntity
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MainOutputs {
    val chargerDetailState: StateFlow<MainChargerDetailState>
    val searchFilterState: StateFlow<MainSearchFilterState>
    val chargerInfoState: StateFlow<MainChargerInfoState>
    val geocoderEvent: SharedFlow<String>
    val searchEvent: SharedFlow<List<ChargerInfo>>
    val mainEffects: SharedFlow<MainEffect>
}

sealed class MainChargerDetailState {
    object Initial : MainChargerDetailState()
    data class Main(val chargerDetail: ChargerDetailEntity) : MainChargerDetailState()
}

sealed class MainSearchFilterState {
    object Initial : MainSearchFilterState()
    data class Main(val searchFilters: MainSearchFilterEntity) : MainSearchFilterState()
}

sealed class MainChargerInfoState {
    object Initial : MainChargerInfoState()
    data class Main(val chargerInfo: List<ChargerInfo>) : MainChargerInfoState()
}

sealed class MainEffect {
    data class SearchText(val text: String): MainEffect()
    data class MoveCamera(val position: LatLng, val zoom: Float): MainEffect()
    data class ShowDetail(val chargerDetail: ChargerDetailEntity): MainEffect()
}

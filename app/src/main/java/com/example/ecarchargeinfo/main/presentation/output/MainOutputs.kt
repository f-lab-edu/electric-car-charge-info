package com.example.ecarchargeinfo.main.presentation.output

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import kotlinx.coroutines.flow.StateFlow

interface MainOutputs {
    val searchFilterState: StateFlow<MainSearchFilterState>
}

sealed class MainSearchFilterState {
    object Initial: MainSearchFilterState()
    data class Main(val searchFilters: MainSearchFilterEntity): MainSearchFilterState()
}

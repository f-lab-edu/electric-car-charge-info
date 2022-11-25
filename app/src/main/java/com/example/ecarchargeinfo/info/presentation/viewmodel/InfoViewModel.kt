package com.example.ecarchargeinfo.info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.output.InfoOutPuts
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.GetChargerInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor(
    private val getChargerInfoUseCase: GetChargerInfoUseCase
) : ViewModel(), InfoOutPuts {

    val outPuts: InfoOutPuts = this

    private val _chargerInfostate: MutableStateFlow<InfoChargerInfoState> =
        MutableStateFlow(InfoChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<InfoChargerInfoState>
        get() = _chargerInfostate

    fun updateChargerInfo(address: String) {
        viewModelScope.launch {
            _chargerInfostate.value = InfoChargerInfoState.Main(
                getChargerInfoUseCase(address)
            )
        }
    }
}






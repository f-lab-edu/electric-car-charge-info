package com.example.ecarchargeinfo.info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.config.model.ApplicationConstants
import com.example.ecarchargeinfo.info.domain.usecase.copyaddress.CopyAddressUseCase
import com.example.ecarchargeinfo.info.domain.usecase.distance.DistanceUseCase
import com.example.ecarchargeinfo.info.presentation.input.InfoInputs
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.output.InfoOutPuts
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.GetChargerInfoUseCase
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor(
    private val getChargerInfoUseCase: GetChargerInfoUseCase,
    private val copyAddressUseCase: CopyAddressUseCase,
    private val distanceUseCase: DistanceUseCase,

    ) : ViewModel(), InfoOutPuts, InfoInputs {
    val outPuts: InfoOutPuts = this
    val inPuts: InfoInputs = this

    private val _chargerInfostate: MutableStateFlow<InfoChargerInfoState> =
        MutableStateFlow(InfoChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<InfoChargerInfoState>
        get() = _chargerInfostate
    private val _distanceEvent: MutableSharedFlow<String> =
        MutableSharedFlow(
            replay = ApplicationConstants.REPLAY,
            extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW

        )

    override val distanceEvent: SharedFlow<String>
        get() = _distanceEvent


    fun updateChargerInfo(address: String) {
        viewModelScope.launch {
            _chargerInfostate.value = InfoChargerInfoState.Main(
                getChargerInfoUseCase(address)
            )
        }
    }

    fun getRecyclerArray(chargeInfo: List<ChargerInfo>): ArrayList<ChargerInfo> {
        val result = ArrayList<ChargerInfo>()
        chargeInfo.forEach {
            result.add(it)
        }
        return result
    }

    override fun onCopyClick() {
        if (_chargerInfostate.value is InfoChargerInfoState.Main) {
            _chargerInfostate.value.let {
                if (it is InfoChargerInfoState.Main) {
                    copyAddressUseCase(it.chargerInfo[0].addr)
                }
            }
        }
    }

    fun updateDistance(location: LatLng, chargerLocation: LatLng) {
        if (location != null && chargerLocation != null) {
            viewModelScope.launch {
                _distanceEvent.emit(distanceUseCase(location, chargerLocation).toString() + "km")
            }
        }
    }

}

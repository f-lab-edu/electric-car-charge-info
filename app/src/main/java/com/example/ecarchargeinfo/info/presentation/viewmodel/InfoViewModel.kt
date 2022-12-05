package com.example.ecarchargeinfo.info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.config.model.ApplicationConstants
import com.example.ecarchargeinfo.info.domain.model.Charger
import com.example.ecarchargeinfo.info.domain.usecase.copyaddress.CopyAddressUseCase
import com.example.ecarchargeinfo.info.domain.usecase.distance.DistanceUseCase
import com.example.ecarchargeinfo.info.presentation.input.InfoInputs
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.output.InfoEffect
import com.example.ecarchargeinfo.info.presentation.output.InfoOutPuts
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.GetChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.ILocationUseCase
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
    private val getLocationUseCase: ILocationUseCase

) : ViewModel(), InfoOutPuts, InfoInputs {

    val outPuts: InfoOutPuts = this
    val inPuts: InfoInputs = this
    private val _chargerInfoState: MutableStateFlow<InfoChargerInfoState> =
        MutableStateFlow(InfoChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<InfoChargerInfoState>
        get() = _chargerInfoState
    private val _distanceEvent: MutableSharedFlow<String> = MutableSharedFlow(
        replay = ApplicationConstants.REPLAY,
        extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
        onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW

    )
    override val distanceEvent: SharedFlow<String>
        get() = _distanceEvent
    private val _chargersEvent: MutableSharedFlow<List<Charger>> = MutableSharedFlow(
        replay = ApplicationConstants.REPLAY,
        extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
        onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW
    )
    override val chargersEvent: SharedFlow<List<Charger>>
        get() = _chargersEvent
    private val _infoEffect: MutableSharedFlow<InfoEffect> =
        MutableSharedFlow<InfoEffect>(
            replay = ApplicationConstants.REPLAY,
            extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW
        )
    override val infoEffect: SharedFlow<InfoEffect>
        get() = _infoEffect

    fun updateChargerInfo(address: String) {
        viewModelScope.launch {
            val chargerInfo = getChargerInfoUseCase(address)
            _chargerInfoState.value = InfoChargerInfoState.Main(
                chargerInfo = chargerInfo
            )

            _chargersEvent.emit(
                getChargerInfoArray(
                    chargerInfo = chargerInfo
                )
            )
        }
    }

    fun updateNowLocation(): LatLng = getLocationUseCase()

    private fun getChargerInfoArray(chargerInfo: List<ChargerInfo>): List<Charger> =
        mutableListOf<Charger>().also { array ->
            chargerInfo.forEach {
                array.add(
                    Charger(
                        chargeTp = it.chargeTp,
                        cpStat = it.cpStat,
                        cpTp = it.cpTp
                    )
                )
            }
        }

    fun getRecyclerArray(chargeInfo: List<ChargerInfo>): List<ChargerInfo> {
        val result = mutableListOf<ChargerInfo>()
        chargeInfo.forEach {
            result.add(it)
        }
        return result
    }


    fun updateDistance(location: LatLng, chargerLocation: LatLng) {
        viewModelScope.launch {
            _distanceEvent.emit(distanceUseCase(location, chargerLocation).toString() + "km")
        }
    }

    override fun onCopyClick(address: String) {
        viewModelScope.launch {
            copyAddressUseCase(address)
            _infoEffect.emit(InfoEffect.CopyAddress(address))
        }
    }
}

package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainChargerDetailState
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.map.domain.entity.ChargerDetailEntity
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.map.domain.model.ChargerDetailConstants
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.domain.usecase.allmarker.IGetAllMarkerUseCase
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.IChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.filteredmarker.IGetFilteredMarkerUseCase
import com.example.ecarchargeinfo.map.domain.usecase.geocoder.IGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.ILocationUseCase
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class MapViewModel @Inject constructor(
    private val getLocationUseCase: ILocationUseCase,
    private val getGeocoderUseCase: IGeocoderUseCase,
    private val getChargerInfoUseCase: IChargerInfoUseCase,
    private val getFilteredMarkerUseCase: IGetFilteredMarkerUseCase,
    private val getAllMarkerUseCase: IGetAllMarkerUseCase
) :
    ViewModel(), MainInputs, MainOutputs {
    val inputs: MainInputs = this
    val outputs: MainOutputs = this

    private val _searchFilterState: MutableStateFlow<MainSearchFilterState> =
        MutableStateFlow(MainSearchFilterState.Initial)
    override val searchFilterState: StateFlow<MainSearchFilterState>
        get() = _searchFilterState
    private val _chargerInfoState: MutableStateFlow<MainChargerInfoState> =
        MutableStateFlow(MainChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<MainChargerInfoState>
        get() = _chargerInfoState
    private val _chargerDetailState: MutableStateFlow<MainChargerDetailState> =
        MutableStateFlow(MainChargerDetailState.Initial)
    override val chargerDetailState: StateFlow<MainChargerDetailState>
        get() = _chargerDetailState
    private val _geocoderEvent: MutableSharedFlow<String> =
        MutableSharedFlow(
            replay = MapConstants.REPLAY,
            extraBufferCapacity = MapConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = MapConstants.ON_BUFFER_OVERFLOW
        )
    override val geocoderEvent: SharedFlow<String>
        get() = _geocoderEvent
    private val koreaAddressArray = ArrayList<String>()
    private var chargerMarkerArray = ArrayList<MyItem>()


    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    init {
        initSearchFilter()
        initChagerDetail()
    }


    fun clearKoreaAddress() {
        koreaAddressArray.clear()
    }

    fun getMarkerByFiltered(type: String): ArrayList<MyItem> =
        getFilteredMarkerUseCase(chargerMarkerArray, type)


    fun setMarkerArray(list: List<ChargerInfo>) {
        chargerMarkerArray = getAllMarkerUseCase(list)
    }

    fun getMarkerArray(): ArrayList<MyItem> = chargerMarkerArray

    fun updateNowLocation(): LatLng = getLocationUseCase()

    fun updateGeocoding(address: String) {
        if (address != "") {
            viewModelScope.launch {
                val result = getGeocoderUseCase(address)
                if (koreaAddressArray.contains(result)) {
                    return@launch
                } else {
                    koreaAddressArray.add(result)
                    _geocoderEvent.emit(result)
                }
            }
        }
    }

    fun updateChargerInfo(address: String) {
        viewModelScope.launch {
            _chargerInfoState.value = MainChargerInfoState.Main(
                getChargerInfoUseCase(address)
            )
        }
    }

    override fun onMarkerClick(visible: Boolean, markerInfo: MarkerInfo) {
        if (_chargerDetailState.value is MainChargerDetailState.Main) {
            _chargerDetailState.update {
                if (it is MainChargerDetailState.Main) {
                    it.copy(
                        it.chargerDetail.copy(
                            visible = visible,
                            markerInfo = MarkerInfo(
                                cpNm = markerInfo.cpNm,
                                addr = markerInfo.addr,
                                chargeTp = markerInfo.chargeTp,
                                cpStat = markerInfo.cpStat
                            )
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onMarkerClick(visible: Boolean) {
        if (_chargerDetailState.value is MainChargerDetailState.Main) {
            _chargerDetailState.update {
                if (it is MainChargerDetailState.Main) {
                    it.copy(
                        it.chargerDetail.copy(
                            visible = visible
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onComboClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            combo = !it.searchFilters.combo
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onDemoClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            demo = !it.searchFilters.demo
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onACClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            ac = !it.searchFilters.ac
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onSlowClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            slow = !it.searchFilters.slow
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onSpeedClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            speedEntity = it.searchFilters.speedEntity.copy(
                                speed = !it.searchFilters.speedEntity.speed
                            )
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    override fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity) {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                if (it is MainSearchFilterState.Main) {
                    it.copy(
                        searchFilters = it.searchFilters.copy(
                            speedEntity = it.searchFilters.speedEntity.copy(
                                startRange = thisSpeedEntity.startRange,
                                endRange = thisSpeedEntity.endRange
                            )
                        )
                    )
                } else {
                    it
                }
            }
        }
    }

    private fun initSearchFilter() {
        _searchFilterState.value = MainSearchFilterState.Main(
            searchFilters = MainSearchFilterEntity(
                combo = SearchFilter.DEFAULT_COMBO,
                demo = SearchFilter.DEFAULT_DEMO,
                ac = SearchFilter.DEFAULT_AC,
                slow = SearchFilter.DEFAULT_SLOW,
                speedEntity = MainSearchFilterSpeedEntity(
                    speed = SearchFilter.SpeedEntity.DEFAULT_SPEED,
                    startRange = SearchFilter.SpeedEntity.START_RANGE,
                    endRange = SearchFilter.SpeedEntity.END_RANGE
                )
            )
        )
    }

    private fun initChagerDetail() {
        _chargerDetailState.value = MainChargerDetailState.Main(
            chargerDetail = ChargerDetailEntity(
                visible = ChargerDetailConstants.DEFAULT_VISIBLE,
                markerInfo = MarkerInfo(
                    cpNm = ChargerDetailConstants.MarkerInfoConstants.DEFAULT_CPNM,
                    addr = ChargerDetailConstants.MarkerInfoConstants.DEFAULT_ADDR,
                    chargeTp = ChargerDetailConstants.MarkerInfoConstants.DEFAULT_CHARGETP,
                    cpStat = ChargerDetailConstants.MarkerInfoConstants.DEFAULT_CPSTAT
                )
            )
        )
    }
}

package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.util.Log
import android.widget.EditText
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.ecarchargeinfo.config.model.ApplicationConstants
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.*
import com.example.ecarchargeinfo.map.domain.entity.ChargerDetailEntity
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.map.domain.model.ChargerDetailConstants
import com.example.ecarchargeinfo.map.domain.usecase.allmarker.IGetAllMarkerUseCase
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.IChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.filteredmarker.IGetFilteredMarkerUseCase
import com.example.ecarchargeinfo.map.domain.usecase.geocoder.IGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.ILocationUseCase
import com.example.ecarchargeinfo.map.domain.util.MapCluster
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class MapViewModel @Inject constructor(
    private val getLocationUseCase: ILocationUseCase,
    private val getGeocoderUseCase: IGeocoderUseCase,
    private val getChargerInfoUseCase: IChargerInfoUseCase,
    private val getFilteredMarkerUseCase: IGetFilteredMarkerUseCase,
    private val getAllMarkerUseCase: IGetAllMarkerUseCase,
) : ViewModel(), MainInputs, MainOutputs {


    val inputs: MainInputs = this
    val outputs: MainOutputs = this

    var inputAddressText = ObservableField<String>("")
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

    private val _mainEffects: MutableSharedFlow<MainEffect> =
        MutableSharedFlow(
            replay = ApplicationConstants.REPLAY,
            extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW
        )
    override val mainEffects: SharedFlow<MainEffect>
        get() = _mainEffects

    private val _geocoderEvent: MutableSharedFlow<String> =
        MutableSharedFlow(
            replay = ApplicationConstants.REPLAY,
            extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW
        )
    override val geocoderEvent: SharedFlow<String>
        get() = _geocoderEvent
    private val _searchEvent: MutableSharedFlow<List<ChargerInfo>> =
        MutableSharedFlow(
            replay = ApplicationConstants.REPLAY,
            extraBufferCapacity = ApplicationConstants.EXTRA_BUFFER_CAPAVITY,
            onBufferOverflow = ApplicationConstants.ON_BUFFER_OVERFLOW
        )
    override val searchEvent: SharedFlow<List<ChargerInfo>>
        get() = _searchEvent

    private val koreaAddressArray = mutableListOf<String>()
    private var chargerMarkerArray = mutableListOf<MapCluster>()

    init {
        initSearchFilter()
        initChagerDetail()
        observeGeocoder()
    }

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    fun observeGeocoder() {
        viewModelScope.launch {
            geocoderEvent.collect {
                if (it != "") {
                    updateChargerInfo(it).also {
                        println("@@@@@@@@@@@@@" + it.toString())
                    }
                }
            }
        }
    }

    fun clearKoreaAddress() {
        koreaAddressArray.clear()
    }

    fun getMarkerByFiltered(type: String): List<MapCluster> =
        getFilteredMarkerUseCase(chargerMarkerArray, type)

    fun setMarkerArray(list: List<ChargerInfo>) {
        getAllMarkerUseCase(list).forEach {
            chargerMarkerArray.add(it)
        }
    }

    fun clearChargerMarkerArray() {
        chargerMarkerArray.clear()
    }

    fun getMarkerArray(): List<MapCluster> = chargerMarkerArray

    fun updateNowLocation(): LatLng = getLocationUseCase()

    fun updateGeocoding(address: String) {
        if (address != "") {
            viewModelScope.launch {
                val result = getGeocoderUseCase(address)
                println("@@@지오결과" + result)
                if (koreaAddressArray.contains(result)) {
                    return@launch
                } else {
                    koreaAddressArray.add(result)
                    _geocoderEvent.emit(result)
                }
            }
        }
    }

    private suspend fun updateSearchData(cond: String) {
        viewModelScope.launch {
            _searchEvent.emit(getChargerInfoUseCase(cond = cond))
        }
    }

    override fun onSearchButtonClick(searchTxt: String) {
        clearChargerMarkerArray()
        clearKoreaAddress()
        viewModelScope.launch(Dispatchers.Main) {
            _mainEffects.emit(MainEffect.SearchText(searchTxt))
            updateSearchData(cond = searchTxt)
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
                (it as MainChargerDetailState.Main).copy(
                    chargerDetail = it.chargerDetail.copy(
                        visible = visible
                    )
                )
            }
        }
    }

    override fun onSearchButtonClick(searchTxt: String, view: EditText) {
        TODO("Not yet implemented")
    }

    override fun onMoveCamera(position: LatLng, zoom: Float) {
        viewModelScope.launch {
            _mainEffects.emit(
                MainEffect.MoveCamera(
                    position = position,
                    zoom = zoom
                )
            )
        }
    }

    override fun onDetailClick() {
        viewModelScope.launch {
            if (_chargerDetailState.value is MainChargerDetailState.Main) {
                val detailState = _chargerDetailState.value as MainChargerDetailState.Main
                _mainEffects.emit(
                    MainEffect.ShowDetail(
                        chargerDetail = detailState.chargerDetail
                    )
                )
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

    fun getKoreaAddress() = koreaAddressArray

}

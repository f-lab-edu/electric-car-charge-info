package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainKoreanAddressState
import com.example.ecarchargeinfo.main.presentation.output.MainLocationState
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.map.domain.model.LocationConstants
import com.example.ecarchargeinfo.map.domain.model.LocationCoord
import com.example.ecarchargeinfo.map.domain.usecase.GetGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.GetLocationUseCase
import com.example.ecarchargeinfo.map.presentation.ui.GeocoderCallBack
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getGeocoderUseCase: GetGeocoderUseCase
    //private val changeLocationUseCase: ChangeLocationUseCase
) :
    ViewModel(), MainInputs, MainOutputs  {
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
    private val _locationState: MutableStateFlow<MainLocationState> =
        MutableStateFlow(MainLocationState.Initial)
    override val locationState: StateFlow<MainLocationState>
        get() = _locationState
    private val _koreanAddressState: MutableStateFlow<MainKoreanAddressState> =
        MutableStateFlow(MainKoreanAddressState.Initial)
    override val koreanAddressState: StateFlow<MainKoreanAddressState>
        get() = _koreanAddressState

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    init {
        initSearchFilter()
        initLocation()
        getLocation()
        initKoreanAddress()

    }

    fun getLocation() {
        if (_locationState.value is MainLocationState.Main) {
            _locationState.update {
                if (it is MainLocationState.Main) {
                    it.copy(locationInfo = LocationCoord(getLocationUseCase()))
                } else {
                    it
                }
            }
        }
    }

    fun getCoordinateForGeocoder(): String {
        var result = ""
        if (locationState.value is MainLocationState.Main) {
            result =
                ((locationState.value as MainLocationState.Main).locationInfo.coordinate.longitude.toString()) +
                        "," + ((locationState.value as MainLocationState.Main).locationInfo.coordinate.latitude.toString())
        }
        return result
    }
    fun getGeocoder(coords: String) {
        if (_koreanAddressState.value is MainKoreanAddressState.Main) {
            _koreanAddressState.update {
                if (it is MainKoreanAddressState.Main) {
                    it.copy(koreanAddressInfo = getGeocoderUseCase(coords))
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

    private fun initLocation() {
        _locationState.value = MainLocationState.Main(
            locationInfo = LocationCoord(
                coordinate = LatLng(LocationConstants.DEAFAULT_LAT, LocationConstants.DEAFAULT_LON)
            )
        )
    }


    private fun initKoreanAddress() {
        _koreanAddressState.value = MainKoreanAddressState.Main(
            koreanAddressInfo = LocationConstants.SEOUL
        )
    }

    fun text(result: String) {
        println(result)
    }

}

package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainLocationState
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.map.domain.model.LocationConstants
import com.example.ecarchargeinfo.map.domain.model.LocationCoord
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.IChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.geocoder.IGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.ILocationUseCase
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
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
    private val getChargerInfoUseCase: IChargerInfoUseCase
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
    private val _locationState: MutableStateFlow<MainLocationState> =
        MutableStateFlow(MainLocationState.Initial)
    override val locationState: StateFlow<MainLocationState>
        get() = _locationState
    private val _geocoderEvent: MutableSharedFlow<String> =
        MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    override val geocoderEvent: SharedFlow<String>
        get() = _geocoderEvent

    private val marker: ArrayList<Marker> = ArrayList<Marker>()

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    init {
        initSearchFilter()
        initLocation()
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

    private fun getCoordinate(): String =
        if (locationState.value is MainLocationState.Main) {
            ((locationState.value as MainLocationState.Main).locationInfo.coordinate.longitude.toString()) +
                    "," + ((locationState.value as MainLocationState.Main).locationInfo.coordinate.latitude.toString())
        } else {
            ""
        }

    fun updateGeocoding(address: String) {
        if (address != "") {
            viewModelScope.launch {
                _geocoderEvent.emit(getGeocoderUseCase(address))
            }
        }
    }

    fun updateChargerInfo(address: String) {
        println(geocoderEvent)
        viewModelScope.launch {
            _chargerInfoState.value = MainChargerInfoState.Main(
                getChargerInfoUseCase(address)
            )
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
                coordinate = LatLng(
                    LocationConstants.DEAFAULT_LAT,
                    LocationConstants.DEAFAULT_LON
                )
            )
        )
    }
}

package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
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
import com.example.ecarchargeinfo.map.domain.usecase.GetGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.GetLocationUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.security.PrivateKey
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getGeocoderUseCase: GetGeocoderUseCase
    //private val changeLocationUseCase: ChangeLocationUseCase
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

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    init {
        initSearchFilter()
        initLocation()
        getLocation()
    }

    fun initSearchFilter() {
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

    fun test()  {
    }

    fun initLocation() {
        _locationState.value = MainLocationState.Main(
            locationInfo = LocationCoord(
                coordinate = LatLng(LocationConstants.DEAFAULT_LAT, LocationConstants.DEAFAULT_LON)
            )
        )
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


    fun getGeocoder(coords: String): String = getGeocoderUseCase(coords)

    /*fun changeLocation(context: Context) {
        if (_locationState.value is MainLocationState.Main) {
            _locationState.update {
                if (it is MainLocationState.Main) {
                    it.copy(
                        locationInfo = changeLocationUserCase(context)
                    )
                } else {
                    it
                }
            }
        }
    }*/


    override fun onComboClick() {
        TODO("Not yet implemented")
    }

    override fun onDemoClick() {
        TODO("Not yet implemented")
    }

    override fun onACClick() {
        TODO("Not yet implemented")
    }

    override fun onSlowClick() {
        TODO("Not yet implemented")
    }

    override fun onSpeedClick() {
        TODO("Not yet implemented")
    }

    override fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity) {
        TODO("Not yet implemented")
    }


}
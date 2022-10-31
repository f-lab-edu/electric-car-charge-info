package com.example.ecarchargeinfo.main.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.config.ApplicationClass
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.retrofit.IRetrofit
import com.example.ecarchargeinfo.retrofit.model.ChargerInfo
import com.example.ecarchargeinfo.retrofit.model.MapResponse
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(), MainInputs, MainOutputs {
    val inputs: MainInputs = this
    val outputs: MainOutputs = this
    private val coroutineExceptionHandler = handleException()
    private val _searchFilterState: MutableStateFlow<MainSearchFilterState> =
        MutableStateFlow(MainSearchFilterState.Initial)
    override val searchFilterState: StateFlow<MainSearchFilterState>
        get() = _searchFilterState
    private val _chargerInfoState: MutableStateFlow<MainChargerInfoState> =
        MutableStateFlow(MainChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<MainChargerInfoState>
        get() = _chargerInfoState


    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }


    init {
        initSearchFilter()
    }

    fun getApiAll(cond: String) {
        val service = ApplicationClass.sRetrofit.create(IRetrofit::class.java)
        service.getInfo(1, 100, cond, ApplicationClass.API_KEY).enqueue(object :
            Callback<MapResponse> {
            override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _chargerInfoState.value = MainChargerInfoState.Main(
                            it.data
                        )
                    }
                }
            }

            override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
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

    @SuppressLint("MissingPermission")
    override fun getLatLng(context: Context): LatLng? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val currentLatLng =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        var location: LatLng? = null
        currentLatLng?.let {
            location = LatLng(currentLatLng.latitude, currentLatLng.longitude)
        }
        location?.let {
            return location
        }
        return null
    }


}

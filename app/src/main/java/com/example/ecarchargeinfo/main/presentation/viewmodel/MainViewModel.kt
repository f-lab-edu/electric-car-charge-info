package com.example.ecarchargeinfo.main.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecarchargeinfo.config.ApplicationClass
import com.example.ecarchargeinfo.config.ApplicationClass.Companion.sRetrofit
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainLocationState
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.map.domain.model.MapConstants.DEFAULT_LAT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.DEFAULT_LONG
import com.example.ecarchargeinfo.retrofit.IRetrofit
import com.example.ecarchargeinfo.retrofit.model.charger.MapResponse
import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
    private val _locationState : MutableStateFlow<MainLocationState> =
        MutableStateFlow(MainLocationState.Initial)
    override val locationState: StateFlow<MainLocationState>
        get() = _locationState


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
    fun test(clientId: String, clientKey: String, coords: String): String {
        val service = sRetrofit.create(IRetrofit::class.java)
        val call: Call<GeocoderInfo> = service.getGeocoder(clientId,clientKey,coords,"json","addr")
        val result = call.execute().body()?.let {
            (it.results[0].region.area1.name+" "+it.results[0].region.area2.name)
        }
        return result.toString()
    }

    fun getGeocoder(clientId: String, clientKey: String, coords: String) {
        val service = ApplicationClass.sRetrofit.create(IRetrofit::class.java)
        service.getGeocoder(clientId, clientKey, coords, "json", "addr")
            .enqueue(object : Callback<GeocoderInfo> {
                override fun onResponse(
                    call: Call<GeocoderInfo>,
                    response: Response<GeocoderInfo>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val test = it.results[0].region.area1.name + " " +
                                    it.results[0].region.area2.name
                        }
                    }
                }
                override fun onFailure(call: Call<GeocoderInfo>, t: Throwable) {
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
    fun getLatLng(context: Context): LatLng {
        var location = LatLng(DEFAULT_LAT, DEFAULT_LONG)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val currentLatLng = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        currentLatLng?.let {
            location = LatLng(currentLatLng.latitude, currentLatLng.longitude)

        }
        return location
    }

}

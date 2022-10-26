package com.example.ecarchargeinfo.main.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.config.ApplicationClass
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterEntity
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.domain.model.SearchFilter
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.retrofit.IRetrofit
import com.example.ecarchargeinfo.retrofit.model.MapResponse
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
    val apiData = ArrayList<MapResponse>()

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    fun getApiAll(cond: String) {
        val service = ApplicationClass.sRetrofit.create(IRetrofit::class.java)
        service.getInfo(1, 100, cond, ApplicationClass.API_KEY).enqueue(object :
            Callback<MapResponse> {
            override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                if (response.isSuccessful) {
                    println(response.body()?.data?.size?.toInt())
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
                                startRange =  thisSpeedEntity.startRange,
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

    /* if (_searchFilterState.value is MainSearchFilterState.Main) {
            _searchFilterState.update {
                (_searchFilterState.value as MainSearchFilterState.Main).copy(
                    searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                        speedEntity.startRange = thisSpeedEntity.startRange
                        speedEntity.endRange = thisSpeedEntity.endRange
                    }
                )
            }
        }*/


        val onValueChanged = fun(a: Int, b: Int) {
            if (_searchFilterState.value is MainSearchFilterState.Main) {
                _searchFilterState.update {
                    if (it is MainSearchFilterState.Main) {
                        it.copy(
                            searchFilters = it.searchFilters.copy(
                                speedEntity = it.searchFilters.speedEntity.copy(
                                    startRange =  a,
                                    endRange = b
                                )
                            )
                        )
                    } else {
                        it
                    }
                }
            }
        }


    }

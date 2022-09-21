package com.example.ecarchargeinfo.main.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.main.presentation.output.MainOutputs
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), MainInputs, MainOutputs {

    val inputs: MainInputs = this
    val outputs: MainOutputs = this
    private val coroutineExceptionHandler = handleException()

    private val _searchFilterState: MutableStateFlow<MainSearchFilterState> =
        MutableStateFlow(MainSearchFilterState.Initial)

    override val searchFilterState: StateFlow<MainSearchFilterState>
        get() = _searchFilterState

    private fun handleException() = CoroutineExceptionHandler { _, throwable ->
        Log.e("ECarChargeInfo", throwable.message ?: "")
    }

    override fun onComboClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            combo = !this.combo
                        }
                    )
                )
            }
        }
    }

    override fun onDemoClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            combo = !this.combo
                        }
                    )
                )
            }
        }
    }

    override fun onACClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            ac = !this.ac
                        }
                    )
                )
            }
        }
    }

    override fun onSlowClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            slow = !this.slow
                        }
                    )
                )
            }
        }
    }

    override fun onSpeedClick() {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            speedEntity.speed = !this.speedEntity.speed
                        }
                    )
                )
            }
        }
    }

    override fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity) {
        if (_searchFilterState.value is MainSearchFilterState.Main) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _searchFilterState.emit(
                    (_searchFilterState.value as MainSearchFilterState.Main).copy(
                        searchFilters = (searchFilterState.value as MainSearchFilterState.Main).searchFilters.apply {
                            speedEntity.startRange = thisSpeedEntity.startRange
                            speedEntity.endRange = thisSpeedEntity.endRange
                        }
                    )
                )
            }
        }
    }

}

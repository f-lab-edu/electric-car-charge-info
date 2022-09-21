package com.example.ecarchargeinfo.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecarchargeinfo.main.domain.entity.SearchFilterEntity
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {
    val model = SearchFilterEntity()
    private var _combo = MutableLiveData<Boolean>(true)
    private var _demo = MutableLiveData<Boolean>(true)
    private var _ac = MutableLiveData<Boolean>(true)
    private var _slow = MutableLiveData<Boolean>(false)
    private var _speed = MutableLiveData<Boolean>(false)
    private var _startRange = MutableLiveData<Int>(50)
    private var _endRange = MutableLiveData<Int>(350)
    private val _viewInfo = MutableLiveData<Boolean>(false)

    private val __combo: Flow<Boolean> = flow {
        emit(model.combo)
        delay(60000)
    }

    val combo: LiveData<Boolean>
        get() = _combo
    val demo: LiveData<Boolean>
        get() = _demo
    val ac: LiveData<Boolean>
        get() = _ac
    val slow: LiveData<Boolean>
        get() = _slow
    val speed: LiveData<Boolean>
        get() = _speed
    val startRange: LiveData<Int>
        get() = _startRange
    val endRange = _endRange
    val viewInfo = _viewInfo

    init {
        getType("combo")
        getType("demo")
        getType("ac")
        getType("slow")
        getType("speed")
    }

    fun changeComboState() {
        setType("combo")
        getType("combo")
    }

    fun changeDemoState() {
        setType("demo")
        getType("demo")
    }

    fun changeAcState() {
        setType("ac")
        getType("ac")
    }

    fun changeSlowState() {
        setType("slow")
        getType("slow")
    }

    fun changeSpeedState() {
        setType("speed")
        getType("speed")
    }

    fun changeViewInfoState()   {
        setType("viewInfo")
        getType("viewInfo")
    }

    private fun getType(type: String) {
        when (type) {
            "combo" -> _combo.value = model.combo
            "demo" -> _demo.value = model.demo
            "ac" -> _ac.value = model.ac
            "slow" -> _slow.value = model.slow
            "speed" -> _speed.value = model.speed
            "viewInfo" -> _viewInfo.value = model.viewInfo
            else -> return
        }
    }

    private fun setType(type: String) {
        when (type) {
            "combo" -> model.combo = !(model.combo)
            "demo" -> model.demo = !(model.demo)
            "ac" -> model.ac = !(model.ac)
            "slow" -> model.slow = !(model.slow)
            "speed" -> model.speed = !(model.speed)
            "viewInfo" -> model.viewInfo = !(model.viewInfo)
            else -> return
        }
    }

    val onValueChanged = fun(min: Int, max: Int) {
        model.startRange = min
        model.endRange = max
        _startRange.value = model.startRange
        _endRange.value = model.endRange
    }

}

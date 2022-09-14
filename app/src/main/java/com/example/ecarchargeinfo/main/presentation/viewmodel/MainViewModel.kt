package com.example.ecarchargeinfo.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecarchargeinfo.main.domain.entity.SearchFilterEntity
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
    val startRange = _startRange
    val endRange = _endRange

    init {
        getType("combo")
        getType("demo")
        getType("ac")
        getType("slow")
        getType("speed")
    }


    fun changeComboState() {
        getType("combo")
    }

    fun changeDemoState() {
        getType("demo")
    }

    fun changeAcState() {
        getType("ac")
    }

    fun changeSlowState() {
        getType("slow")
    }

    fun changeSpeedState() {
        getType("speed")
    }


    private fun getType(type: String) {
        when (type) {
            "combo" -> _combo.value = model.combo
            "demo" -> _demo.value = model.demo
            "ac" -> _ac.value = model.ac
            "slow" -> _slow.value = model.slow
            "speed" -> _speed.value = model.speed
            else -> return
        }
    }

}

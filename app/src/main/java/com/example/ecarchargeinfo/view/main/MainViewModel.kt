package com.example.ecarchargeinfo.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {
    val model = MaInModel()
    private var _combo = MutableLiveData<Boolean>(true)
    private var _demo = MutableLiveData<Boolean>(true)
    private var _ac = MutableLiveData<Boolean>(true)
    private var _slow = MutableLiveData<Boolean>(false)
    private var _speed = MutableLiveData<Boolean>(false)

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

    init {
        getType("combo")
        getType("demo")
        getType("ac")
        getType("slow")
        getType("speed")
    }


    fun changeComboState() {
        model.changeTypeState("combo")
        getType("combo")
    }

    fun changeDemoState() {
        model.changeTypeState("demo")
        getType("demo")
    }

    fun changeAcState() {
        model.changeTypeState("ac")
        getType("ac")
    }

    fun changeSlowState() {
        model.changeTypeState("slow")
        getType("slow")
    }

    fun changeSpeedState() {
        model.changeTypeState("speed")
        getType("speed")
    }


    private fun getType(type: String) {
        when (type) {
            "combo" -> _combo.value = model.getType(type = type)
            "demo" -> _demo.value = model.getType(type = type)
            "ac" -> _ac.value = model.getType(type = type)
            "slow" -> _slow.value = model.getType(type = type)
            "speed" -> _speed.value = model.getType(type = type)
            else -> return
        }
    }
}

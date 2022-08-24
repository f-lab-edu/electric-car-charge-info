package com.example.ecarchargeinfo.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val model = MaInModel()
    private var _combo = MutableLiveData<Boolean>(true)
    private var _demo = MutableLiveData<Boolean>(true)
    private var _ac = MutableLiveData<Boolean>(true)
    private var _slow = MutableLiveData<Boolean>(false)
    private var _speed = MutableLiveData<Boolean>(false)

    val combo : LiveData<Boolean> = _combo
    val demo : LiveData<Boolean> = _demo
    val ac : LiveData<Boolean> = _ac
    val slow : LiveData<Boolean> = _slow
    val speed : LiveData<Boolean> = _speed


    fun changeComboState()  {
        model.changeTypeState("combo")
        getType("combo")
    }

    fun changeDemoState()   {
        model.changeTypeState("demo")
        getType("demo")
    }

    fun changeAcState() {
        model.changeTypeState("ac")
        getType("ac")
    }

    fun changeSlowState()   {
        model.changeTypeState("slow")
        getType("slow")
    }

    fun changeSpeedState()  {
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
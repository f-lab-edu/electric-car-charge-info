package com.example.ecarchargeinfo.info.presentation.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecarchargeinfo.info.presentation.input.InfoInputs
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.output.InfoOutPuts
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.GetChargerInfoUseCase
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor(
    private val getChargerInfoUseCase: GetChargerInfoUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(), InfoOutPuts, InfoInputs {

    val outPuts: InfoOutPuts = this
    val inPuts: InfoInputs = this

    private val _chargerInfostate: MutableStateFlow<InfoChargerInfoState> =
        MutableStateFlow(InfoChargerInfoState.Initial)
    override val chargerInfoState: StateFlow<InfoChargerInfoState>
        get() = _chargerInfostate


    fun updateChargerInfo(address: String) {
        viewModelScope.launch {
            _chargerInfostate.value = InfoChargerInfoState.Main(
                getChargerInfoUseCase(address)
            )
        }
    }

    fun getRecyclerArray(chargeInfo: List<ChargerInfo>): ArrayList<ChargerInfo> {
        val result = ArrayList<ChargerInfo>()
        chargeInfo.forEach {
            result.add(it)
        }
        return result
    }

    override fun onCopyClick() {
        if(_chargerInfostate.value is InfoChargerInfoState.Main) {

        }
    }
}


/*
val clipboard :ClipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
val clipData  = ClipData.newPlainText(*/


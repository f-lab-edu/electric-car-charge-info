package com.example.ecarchargeinfo.info.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_EMPTY

object InfoBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: TextView, text: String?) {
        text.let {
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("android:cpStatText", "cpStatColor1", "cpStatColor2")
    fun setStatText(view: TextView, text: String?, canColor: Int, cantColor: Int) {
        text.let {
            view.text = when (it) {
                CHARGER_STAT_OK -> CHARGER_STAT_OK_VALUE.also { view.setTextColor(canColor) }
                CHARGER_STAT_ON_UES -> CHARGER_STAT_ON_UES_VALUE.also { view.setTextColor(cantColor) }
                CHARGER_STAT_BREAK -> CHARGER_STAT_BREAK_VALUE.also { view.setTextColor(cantColor) }
                CHARGER_STAT_NETWORK_ERROR -> CHARGER_STAT_NETWORK_ERROR_VALUE.also { view.setTextColor(cantColor) }
                CHARGER_STAT_NETWORK_DISCONNECT -> CHARGER_STAT_NETWORK_DISCONNECT_VALUE.also { view.setTextColor(cantColor) }
                else -> CHARGER_STAT_EMPTY.also { view.setTextColor(cantColor) }
            }
        }
    }
}

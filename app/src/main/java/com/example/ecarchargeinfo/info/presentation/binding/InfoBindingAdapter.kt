package com.example.ecarchargeinfo.info.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_1
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_1_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_2
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_2_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_3
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_3_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_4
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_4_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_5
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_5_VALUE
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
    @BindingAdapter("android:cpStatText")
    fun setStatText(view: TextView, text: String?) {
        text.let {
            view.text = when (it) {
                CHARGER_STAT_1 -> CHARGER_STAT_1_VALUE
                CHARGER_STAT_2 -> CHARGER_STAT_2_VALUE
                CHARGER_STAT_3 -> CHARGER_STAT_3_VALUE
                CHARGER_STAT_4 -> CHARGER_STAT_4_VALUE
                CHARGER_STAT_5 -> CHARGER_STAT_5_VALUE
                else -> CHARGER_STAT_EMPTY
            }
        }
    }
}

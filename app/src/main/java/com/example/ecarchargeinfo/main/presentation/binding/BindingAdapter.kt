package com.example.ecarchargeinfo.main.presentation.binding

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_0_VALUE
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_1
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_10
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.DC_TYPE_DEMO_COMBO_AC
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.B_TYPE_5PIN
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_2
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.C_TYPE_5PIN
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_3
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.BC_TYPE_5PIN
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_4
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.BC_TYPE_7PIN
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_5
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.DC_TYPE_DEMO
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_6
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.AC_TYPE
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_7
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.DC_TYPE_COMBO
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_8
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.DC_TYPE_DEMO_COMBO
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.CPTP_CODE_9
import com.example.ecarchargeinfo.info.domain.entity.ChargeTpConstants.DC_TYPE_DEMO_AC
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_FAST
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_EMPTY
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES_VALUE
import com.google.android.material.slider.RangeSlider

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun isGone(view: View, shouldBeGone: Boolean) {
        view.visibility =
            if (shouldBeGone) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    @JvmStatic
    @BindingAdapter("background")
    fun setBackground(view: View, shouldBeChange: Boolean) {
        view.setBackgroundResource(
            if (shouldBeChange) {
                R.drawable.custom_button_true
            } else {
                R.drawable.custom_button_false
            }
        )
    }

    @JvmStatic
    @BindingAdapter("range")
    fun RangeSlider.OnChangeListener(inputs: MainInputs) {
        addOnChangeListener { _, _, _ ->
            val firstValue = this.values[0].toInt()
            val lastValue = this.values[1].toInt()

            inputs.onSpeedChange(
                MainSearchFilterSpeedEntity(
                    speed = true,
                    startRange = firstValue,
                    endRange = lastValue
                )
            )
        }
    }

    @JvmStatic
    @BindingAdapter("slowFastImageResource")
    fun setChargerTypeImage(view: ImageView, chargeTp: String?) {
        chargeTp.let {
            view.setImageResource(
                if (it == CHARGER_TYPE_FAST) {
                    R.drawable.volt
                } else {
                    R.drawable.volt_slow
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("chargerStat")
    fun setChargerStat(view: TextView, cpStat: String?) {
        view.context.resources.getColor(Color.alpha(R.color.can_charge), null)

        cpStat.let {
            view.setTextColor(
                when (cpStat) {
                    CHARGER_STAT_OK -> {
                        canColor.also { view.text = CHARGER_STAT_OK_VALUE }
                    }
                    CHARGER_STAT_ON_UES -> {
                        cantColor.also { view.text = CHARGER_STAT_ON_UES_VALUE }
                    }
                    CHARGER_STAT_BREAK -> {
                        cantColor.also { view.text = CHARGER_STAT_BREAK_VALUE }
                    }
                    CHARGER_STAT_NETWORK_ERROR -> {
                        cantColor.also { view.text = CHARGER_STAT_NETWORK_ERROR_VALUE }
                    }
                    CHARGER_STAT_NETWORK_DISCONNECT -> {
                        cantColor.also { view.text = CHARGER_STAT_NETWORK_DISCONNECT_VALUE }
                    }
                    else -> {
                        cantColor.also { view.text = CHARGER_STAT_EMPTY }
                    }

                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("chargerType")
    fun setChargerType(view: TextView, chargeTp: String) {
        view.text = when (chargeTp) {
            CPTP_CODE_1 -> B_TYPE_5PIN
            CPTP_CODE_2 -> C_TYPE_5PIN
            CPTP_CODE_3 -> BC_TYPE_5PIN
            CPTP_CODE_4 -> BC_TYPE_7PIN
            CPTP_CODE_5 -> DC_TYPE_DEMO
            CPTP_CODE_6 -> AC_TYPE
            CPTP_CODE_7 -> DC_TYPE_COMBO
            CPTP_CODE_8 -> DC_TYPE_DEMO_COMBO
            CPTP_CODE_9 -> DC_TYPE_DEMO_AC
            CPTP_CODE_10 -> DC_TYPE_DEMO_COMBO_AC
            else -> CPTP_CODE_0_VALUE.also {
                view.setTextColor(Color.alpha(R.color.red))
            }
        }
    }
}


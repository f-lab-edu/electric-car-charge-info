package com.example.ecarchargeinfo.main.presentation.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.main.presentation.input.MainInputs
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK_VALUE
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
                if (it == "2") {
                    R.drawable.volt
                } else {
                    R.drawable.volt_slow
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("chargerStat", "canColor", "cantColor")
    fun setChargerStat(view: TextView, cpStat: String?, canColor: Int, cantColor: Int) {
        cpStat.let {
            view.setTextColor(
                if (cpStat == CHARGER_STAT_OK_VALUE) {
                    canColor.also { view.text = CHARGER_STAT_OK_VALUE }
                } else {
                    cantColor.also { view.text = CHARGER_STAT_ON_UES_VALUE }
                }
            )
        }
    }

}

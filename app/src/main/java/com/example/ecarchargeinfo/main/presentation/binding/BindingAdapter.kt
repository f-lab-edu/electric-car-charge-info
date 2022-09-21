package com.example.ecarchargeinfo.main.presentation.binding

import android.view.View
import androidx.lifecycle.LiveData
import com.example.ecarchargeinfo.R
import androidx.databinding.BindingAdapter
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
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
    fun RangeSlider.OnChangeListener(onSpeedChange: (MainSearchFilterSpeedEntity) -> Unit) {
        addOnChangeListener { _, _, _ ->
            val firstValue = this.values[0].toInt()
            val lastValue = this.values[1].toInt()

            onSpeedChange (
                MainSearchFilterSpeedEntity (
                    speed = true,
                    startRange = firstValue,
                    endRange = lastValue
                )
            )
        }
    }



}

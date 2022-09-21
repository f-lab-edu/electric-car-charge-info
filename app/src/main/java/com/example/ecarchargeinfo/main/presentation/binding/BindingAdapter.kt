package com.example.ecarchargeinfo.main.presentation.binding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.example.ecarchargeinfo.R
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.fragment.app.Fragment
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.MapView
import com.google.android.material.slider.RangeSlider

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun isGone(view: View, shouldBeGone: LiveData<Boolean>) {
        view.visibility =
            if (shouldBeGone.value == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    @JvmStatic
    @BindingAdapter("background")
    fun setBackground(view: View, shouldBeChange: LiveData<Boolean>) {
        view.setBackgroundResource(
            if (shouldBeChange.value == true) {
                R.drawable.custom_button_true
            } else {
                R.drawable.custom_button_false
            }
        )
    }

    @JvmStatic
    @BindingAdapter("range")
    fun RangeSlider.OnChangeListener(function: (Int, Int) -> Unit) {
        addOnChangeListener { slider, value, fromUser ->
            val firstValue = this.values[0].toInt()
            val lastValue = this.values[1].toInt()
            function(firstValue, lastValue)
        }
    }



}

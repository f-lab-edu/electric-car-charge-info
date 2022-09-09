package com.example.ecarchargeinfo.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.ecarchargeinfo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.slider.RangeSlider

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun isGone(view: View, shouldBeGone: LiveData<Boolean>) {
        view.visibility =
            if (shouldBeGone.value == false) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    @JvmStatic
    @BindingAdapter("background")
    fun background(view: View, shouldBeChange: LiveData<Boolean>) {
        if (shouldBeChange.value == true) {
            view.setBackgroundResource(R.drawable.custom_button_true)
        } else {
            view.setBackgroundResource(R.drawable.custom_button_false)
        }
    }

}

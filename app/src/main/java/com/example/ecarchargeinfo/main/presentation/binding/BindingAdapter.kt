package com.example.ecarchargeinfo.main.presentation.binding

import android.view.View
import androidx.lifecycle.LiveData
import com.example.ecarchargeinfo.R

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

}

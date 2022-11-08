package com.example.ecarchargeinfo.map.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ecarchargeinfo.map.domain.usecase.GetLocationUseCase
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MapViewModel @Inject constructor(private val getLocationUseCase: GetLocationUseCase) : ViewModel() {
    fun getLocation(context: Context): LatLng = getLocationUseCase(context)

}
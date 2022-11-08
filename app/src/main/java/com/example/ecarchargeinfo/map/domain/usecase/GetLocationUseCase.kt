package com.example.ecarchargeinfo.map.domain.usecase

import android.content.Context
import com.example.ecarchargeinfo.map.domain.repository.GetLocationRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val getLocationRepositoryImpl: GetLocationRepositoryImpl
) {
    operator fun invoke(context: Context): LatLng =
        getLocationRepositoryImpl.getLocation(context)
}
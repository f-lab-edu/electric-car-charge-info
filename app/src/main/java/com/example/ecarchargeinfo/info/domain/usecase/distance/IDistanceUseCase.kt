package com.example.ecarchargeinfo.info.domain.usecase.distance

import com.google.android.gms.maps.model.LatLng

interface IDistanceUseCase {
    operator fun invoke(location: LatLng, chargerLocation: LatLng): Double
}

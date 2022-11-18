package com.example.ecarchargeinfo.map.domain.usecase.location

import com.google.android.gms.maps.model.LatLng

interface ILocationUseCase {
    operator fun invoke(): LatLng
}

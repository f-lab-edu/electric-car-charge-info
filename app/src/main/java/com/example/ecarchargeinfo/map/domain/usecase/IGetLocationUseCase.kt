package com.example.ecarchargeinfo.map.domain.usecase

import com.google.android.gms.maps.model.LatLng

interface IGetLocationUseCase {
    operator fun invoke(): LatLng
}
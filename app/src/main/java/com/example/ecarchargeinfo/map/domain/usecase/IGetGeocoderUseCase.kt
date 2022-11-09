package com.example.ecarchargeinfo.map.domain.usecase

interface IGetGeocoderUseCase {
    operator fun invoke(coords: String): String
}
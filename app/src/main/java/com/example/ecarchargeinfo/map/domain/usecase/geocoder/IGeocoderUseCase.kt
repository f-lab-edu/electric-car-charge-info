package com.example.ecarchargeinfo.map.domain.usecase.geocoder

interface IGeocoderUseCase {
    suspend operator fun invoke(coords: String): String
}


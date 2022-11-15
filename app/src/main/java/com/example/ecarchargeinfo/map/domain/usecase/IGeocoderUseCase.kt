package com.example.ecarchargeinfo.map.domain.usecase

interface IGeocoderUseCase {
    operator fun invoke(coords: String): String
}


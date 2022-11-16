package com.example.ecarchargeinfo.map.domain.usecase

interface IGeocoderUseCase {
    suspend operator fun invoke(coords: String): String
}


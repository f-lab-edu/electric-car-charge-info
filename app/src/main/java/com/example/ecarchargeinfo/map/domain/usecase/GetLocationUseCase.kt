package com.example.ecarchargeinfo.map.domain.usecase

import android.content.Context
import com.example.ecarchargeinfo.map.domain.repository.GetLocationRepository
import com.example.ecarchargeinfo.map.domain.repository.GetLocationRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val getLocationRepository: GetLocationRepository,
) : IGetLocationUseCase {
    override fun invoke(): LatLng = getLocationRepository.getLocation()

}
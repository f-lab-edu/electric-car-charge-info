package com.example.ecarchargeinfo.info.domain.usecase.copyaddress

import com.example.ecarchargeinfo.info.domain.repository.copyaddress.CopyAddressRepository
import javax.inject.Inject

class CopyAddressUseCase @Inject constructor(
    private val repository: CopyAddressRepository
) : ICopyAddressUseCase {
    override fun invoke(address: String) {
        repository.copyAddress(address)
    }
}

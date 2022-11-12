package com.example.ecarchargeinfo.map.domain.repository

import javax.inject.Inject

class ChangeRepositoryImpl @Inject constructor() : ChangeLocationRepository {
    override fun test() {
        println("test")
    }
}
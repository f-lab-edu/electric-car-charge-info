package com.example.ecarchargeinfo.config.model

import kotlinx.coroutines.channels.BufferOverflow

object ApplicationConstants {
    const val BASE_URL = "https://api.odcloud.kr/"

    //SharedFlow
    const val REPLAY = 0
    const val EXTRA_BUFFER_CAPAVITY = 1
    val ON_BUFFER_OVERFLOW = BufferOverflow.DROP_OLDEST
}

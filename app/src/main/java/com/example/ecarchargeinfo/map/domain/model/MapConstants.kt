package com.example.ecarchargeinfo.map.domain.model

object MapConstants {
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_LAT = 37.5666805
    const val DEFAULT_LONG = 126.9784147
    const val CLUSTER_ZOOM = 12f
    const val MARKER_ZOOM = 17f
    const val IMAGE_WIDTH = 120
    const val IMAGE_HEIGHT = 120

    const val CHARGER_TYPE_COMBO = "7"
    const val CHARGER_TYPE_DEMO = "5"
    const val CHARGER_TYPE_AC = "6"
    const val CHARGER_TYPE_SLOW = "1"
    const val CHARGER_TYPE_FAST = "2"
    const val CHARGER_CHARGING = "3"

    //Charger stat
    object ChargerStat {
        const val CHARGER_STAT_OK = "1"
        const val CHARGER_STAT_AVAILABLE = "충전가능"
        const val CHARGER_STAT_ON_UES = "2"
        const val CHARGER_STAT_CHARGING = "충전중"
        const val CHARGER_STAT_BREAK = "3"
        const val CHARGER_STAT_FAULT_MAINT = "고장/점검"
        const val CHARGER_STAT_NETWORK_ERROR = "4"
        const val CHARGER_STAT_NETWORK_ERROR_VALUE = "통신장애"
        const val CHARGER_STAT_NETWORK_DISCONNECT = "5"
        const val CHARGER_STAT_NETWORK_DISCONNECT_VALUE = "통신미연결"
        const val CHARGER_STAT_EMPTY = "상태 조회 불가"
    }

}

object ChargerDetailConstants {
    const val DEFAULT_VISIBLE = false

    object MarkerInfoConstants {
        const val DEFAULT_CPNM = "cpNm"
        const val DEFAULT_ADDR = "addr"
        const val DEFAULT_CHARGETP = "chargeTp"
        const val DEFAULT_CPSTAT = "cpStat"
    }
}

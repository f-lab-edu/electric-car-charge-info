package com.example.ecarchargeinfo.info.domain.enum

import android.content.Context
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.map.domain.model.MapConstants

enum class ChargerStat(
    val status: String,
    val message: String,
    val colorResource: Int
) {
    CHARGER_STAT_OK(
        status = MapConstants.ChargerStat.CHARGER_STAT_OK,
        message = MapConstants.ChargerStat.CHARGER_STAT_AVAILABLE,
        colorResource = R.color.can_charge
    ),
    CHARGER_STAT_ON_UES(
        status = MapConstants.ChargerStat.CHARGER_STAT_ON_UES,
        message = MapConstants.ChargerStat.CHARGER_STAT_CHARGING,
        colorResource = R.color.cannot_charge
    ),
    CHARGER_STAT_BREAK(
        status = MapConstants.ChargerStat.CHARGER_STAT_BREAK,
        message = MapConstants.ChargerStat.CHARGER_STAT_FAULT_MAINT,
        colorResource = R.color.cannot_charge
    ),
    CHARGER_STAT_NETWORK_ERROR(
        status = MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR,
        message = MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE,
        colorResource = R.color.cannot_charge
    ),
    CHARGER_STAT_NETWORK_DISCONNECT(
        status = MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT,
        message = MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE,
        colorResource = R.color.cannot_charge
    ),
    CHARGER_STAT_EMPTY(
        status = "",
        message = MapConstants.ChargerStat.CHARGER_STAT_EMPTY,
        colorResource = R.color.cannot_charge
    )
}

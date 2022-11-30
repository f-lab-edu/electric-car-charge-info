package com.example.ecarchargeinfo.map.domain.repository.allmarker

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo

interface AllMarkerRepository {
    fun getAllMarker(list: List<ChargerInfo>): List<MyItem>
    fun getMarkerImage(chargeTp: String): BitmapDrawable
    fun getResizedImage(markerImage: BitmapDrawable): Bitmap
}

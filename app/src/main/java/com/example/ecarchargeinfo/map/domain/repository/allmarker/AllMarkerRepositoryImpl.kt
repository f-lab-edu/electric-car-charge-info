package com.example.ecarchargeinfo.map.domain.repository.allmarker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.res.ResourcesCompat
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AllMarkerRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AllMarkerRepository {
    override fun getAllMarker(list: List<ChargerInfo>): ArrayList<MyItem> {
        val markerArray = ArrayList<MyItem>()
        list.forEachIndexed { index, chargerInfo ->
            if (index > 0) {
                if (list[index - 1].csNm == chargerInfo.csNm) return@forEachIndexed
            }
            val markerImage = getMarkerImage(chargerInfo.chargeTp)

            val item = MyItem(
                _position = LatLng(chargerInfo.lat.toDouble(), chargerInfo.longi.toDouble()),
                _title = chargerInfo.csNm,
                _snippet = chargerInfo.cpStat,
                _icon = BitmapDescriptorFactory.fromBitmap(
                    getResizedImage(
                        getMarkerImage(chargeTp = chargerInfo.chargeTp)
                    )
                ),
                _addr = chargerInfo.addr,
                _chargeTp = chargerInfo.chargeTp,
                _cpTp = chargerInfo.cpTp
            )
            markerArray.add(item)
        }
        return markerArray
    }

    override fun getMarkerImage(chargeTp: String): BitmapDrawable = ResourcesCompat.getDrawable(
        context.resources, when (chargeTp) {
            MapConstants.CHARGER_TYPE_SLOW -> R.drawable.volt_slow
            else -> R.drawable.volt
        }, null
    ) as BitmapDrawable

    override fun getResizedImage(markerImage: BitmapDrawable): Bitmap =
        markerImage.let {
            Bitmap.createScaledBitmap(
                /* src = */ markerImage.bitmap,
                /* dstWidth = */ MapConstants.IMAGE_WIDTH,
                /* dstHeight = */ MapConstants.IMAGE_HEIGHT,
                /* filter = */ false
            )
        }


}

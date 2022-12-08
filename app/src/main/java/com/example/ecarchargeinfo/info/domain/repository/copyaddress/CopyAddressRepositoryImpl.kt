package com.example.ecarchargeinfo.info.domain.repository.copyaddress

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CopyAddressRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    CopyAddressRepository {
    override fun copyAddress(address: String) {
        val clipboard: ClipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("address", address.trim())
        clipboard.setPrimaryClip(clipData)
    }
}

package com.example.ecarchargeinfo.info.domain.repository.copyaddress

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.example.ecarchargeinfo.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CopyAddressRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    CopyAddressRepository {
    override fun copyAddress(address: String) {
        val clipboard: ClipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        try {
            val clipData = ClipData.newPlainText("address", address.trim())
            clipboard.setPrimaryClip(clipData)
            Toast.makeText(
                context, context.resources.getText(R.string.complete_copy), Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                context, context.resources.getText(R.string.incomplete_copy), Toast.LENGTH_SHORT
            ).show()
        }
    }


}

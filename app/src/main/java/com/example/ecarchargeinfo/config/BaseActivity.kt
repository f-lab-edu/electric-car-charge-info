package com.example.ecarchargeinfo.config

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.main.presentation.helper.PermissionHelper
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity

open class BaseActivity: AppCompatActivity() {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.LOCATION_PERMISSION
            && grantResults.size == PermissionHelper.REQUEST_PERMISSION.size) {
            var checkResult = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }
            if (!checkResult) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        PermissionHelper.REQUEST_PERMISSION[0]
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, PermissionHelper.REQUEST_PERMISSION[1])
                ) {
                    Toast.makeText(this, R.string.denied_permission, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "권한이 거부되었습니다. 다시실행해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

package com.example.ecarchargeinfo.main.presentation.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.config.BaseActivity
import com.example.ecarchargeinfo.databinding.ActivityMainBinding
import com.example.ecarchargeinfo.main.presentation.helper.PermissionHelper
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mapFragment = MapFragment()

    companion object {
        const val LOCATION_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        PermissionHelper.checkPermission(this@MainActivity)
        supportFragmentManager.beginTransaction().replace(R.id.main_map, mapFragment, "MAP")
            .commit()
    }
}



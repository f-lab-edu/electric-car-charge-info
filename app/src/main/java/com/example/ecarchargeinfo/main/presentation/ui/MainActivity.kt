package com.example.ecarchargeinfo.main.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.config.BaseActivity
import com.example.ecarchargeinfo.databinding.ActivityMainBinding
import com.example.ecarchargeinfo.main.presentation.helper.PermissionHelper
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.main.presentation.viewmodel.MainViewModel
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mapFragment = MapFragment()
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    companion object {
        const val LOCATION_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        observeUIState()
        binding.inputs = viewModel.inputs
        binding.lifecycleOwner = this
        PermissionHelper.checkPermission(this@MainActivity)
        supportFragmentManager.beginTransaction().replace(R.id.main_map, mapFragment, "MAP")
            .commit()
        binding.btn1.setOnClickListener {
            mapFragment.initMap()
        }

    }

    private fun observeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.outputs.searchFilterState.collect {
                    if (it is MainSearchFilterState.Main) {
                        binding.searchFilterEntity = it.searchFilters
                    }
                }
            }
        }
    }
}

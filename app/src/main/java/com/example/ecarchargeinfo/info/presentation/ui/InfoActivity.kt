package com.example.ecarchargeinfo.info.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityInfoBinding
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding

    @Inject
    lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        binding.lifecycleOwner = this
        val getAddress = intent.getStringExtra("address")
        viewModel.updateChargerInfo(getAddress.toString())
        observeChargerInfoState()

    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.RESUMED) {
                viewModel.outPuts.chargerInfoState.collect() {
                    if (it is InfoChargerInfoState.Main) {
                        binding.chargeInfo = it.chargerInfo.get(0)
                    }
                }
            }

        }
    }
}

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
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding

    @Inject
    lateinit var viewModel: InfoViewModel
    lateinit var chargerLocation: LatLng
    lateinit var currentLocation: LatLng
    lateinit var getAddress: String
    lateinit var getLocationLat: String
    lateinit var getLocationLon: String
    lateinit var distance: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        binding.lifecycleOwner = this
        binding.inputs = viewModel.inPuts

        initIntent()
        if (getLocationLat != null && getLocationLon != null)
            currentLocation = LatLng(getLocationLat.toDouble(), getLocationLon.toDouble())
        observeChargerInfoState()
        viewModel.updateChargerInfo(getAddress.toString())
        observeDistance()
    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.RESUMED) {
                viewModel.outPuts.chargerInfoState.collect() {
                    if (it is InfoChargerInfoState.Main) {
                        binding.chargeInfo = it.chargerInfo.get(0)
                        viewModel.getRecyclerArray(it.chargerInfo)
                        chargerLocation = LatLng(
                            it.chargerInfo[0].lat.toDouble(),
                            it.chargerInfo[0].longi.toDouble()
                        )
                        viewModel.updateDistance(currentLocation, chargerLocation)
                    }
                }
            }

        }
    }

    private fun initIntent() {
        getAddress = intent.getStringExtra("address").toString()
        getLocationLat = intent.getStringExtra("lat").toString()
        getLocationLon = intent.getStringExtra("lon").toString()
    }

    private fun observeDistance()   {
        lifecycleScope.launchWhenStarted {
            viewModel.distanceEvent.collect {
                binding.tvDistance.text = it
            }
        }
    }

}

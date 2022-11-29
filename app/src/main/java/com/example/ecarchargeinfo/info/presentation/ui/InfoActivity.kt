package com.example.ecarchargeinfo.info.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityInfoBinding
import com.example.ecarchargeinfo.info.domain.model.Charger
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.viewmodel.InfoViewModel
import com.example.ecarchargeinfo.info.util.ChargerAdapter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
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
    lateinit var adapter: ChargerAdapter
    lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        binding.lifecycleOwner = this
        binding.inputs = viewModel.inPuts
        layoutManager = GridLayoutManager(applicationContext, 2)
        initIntent()
        if (getLocationLat != null && getLocationLon != null)
            currentLocation = LatLng(getLocationLat.toDouble(), getLocationLon.toDouble())
        observeChargerInfoState()
        viewModel.updateChargerInfo(getAddress.toString())
        observeDistance()
        observeChargers()
    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.RESUMED) {
                viewModel.outPuts.chargerInfoState.collect() {
                    if (it is InfoChargerInfoState.Main) {
                        binding.chargeInfo = it.chargerInfo.get(0)
                        viewModel.getRecyclerArray(chargeInfo = it.chargerInfo)
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

    private fun observeDistance() {
        lifecycleScope.launchWhenStarted {
            viewModel.distanceEvent.collect {
                binding.tvDistance.text = it
            }
        }
    }

    private fun observeChargers() {
        lifecycleScope.launchWhenStarted {
            viewModel.chargersEvent.collect() {
                binding.infoRc.adapter = ChargerAdapter(it)
                binding.infoRc.layoutManager = layoutManager
            }
        }
    }

}

package com.example.ecarchargeinfo.info.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityInfoBinding
import com.example.ecarchargeinfo.info.presentation.output.InfoChargerInfoState
import com.example.ecarchargeinfo.info.presentation.output.InfoEffect
import com.example.ecarchargeinfo.info.presentation.viewmodel.InfoViewModel
import com.example.ecarchargeinfo.info.util.ChargerAdapter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding

    @Inject
    lateinit var infoViewModel: InfoViewModel
    lateinit var chargerLocation: LatLng
    lateinit var getAddress: String
    lateinit var distance: String
    lateinit var adapter: ChargerAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var currentLocation: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        binding.lifecycleOwner = this
        binding.inputs = infoViewModel.inPuts
        initIntent()
        currentLocation = infoViewModel.updateNowLocation()
        observeChargerInfoState()
        observeDistance()
        observeChargers()
        observeUIEffect()
        infoViewModel.updateChargerInfo(getAddress.toString())
    }

    private fun observeUIEffect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                infoViewModel.outPuts.infoEffect.collect {
                    when (it) {
                        is InfoEffect.CopyAddress -> {
                            Toast.makeText(this@InfoActivity, resources.getText(R.string.complete_copy), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.RESUMED) {
                infoViewModel.outPuts.chargerInfoState.collect() {
                    if (it is InfoChargerInfoState.Main) {
                        binding.chargeInfo = it.chargerInfo.get(0)
                        infoViewModel.getRecyclerArray(chargeInfo = it.chargerInfo)
                        chargerLocation = LatLng(
                            it.chargerInfo[0].lat.toDouble(),
                            it.chargerInfo[0].longi.toDouble()
                        )
                        infoViewModel.updateDistance(currentLocation, chargerLocation)
                    }
                }
            }

        }
    }

    private fun initIntent() {
        getAddress = intent.getStringExtra("address").toString()
    }

    private fun observeDistance() {
        lifecycleScope.launchWhenStarted {
            infoViewModel.distanceEvent.collect {
                binding.tvDistance.text = it
            }
        }
    }

    private fun observeChargers() {
        lifecycleScope.launchWhenStarted {
            infoViewModel.chargersEvent.collect() {
                layoutManager = GridLayoutManager(applicationContext, 2)
                binding.infoRc.adapter = ChargerAdapter(it)
                binding.infoRc.layoutManager = layoutManager
            }
        }
    }

}

package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentMapBinding
import com.example.ecarchargeinfo.info.presentation.ui.InfoActivity
import com.example.ecarchargeinfo.main.presentation.output.MainChargerDetailState
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainEffect
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_AC
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_COMBO
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_DEMO
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_SLOW
import com.example.ecarchargeinfo.map.domain.util.MapCluster
import com.example.ecarchargeinfo.map.domain.util.MyClusterManager
import com.example.ecarchargeinfo.map.presentation.viewmodel.MapViewModel
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMapClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var mainActivity: MainActivity? = null
    private lateinit var clusterManager: MyClusterManager<MapCluster>

    private val mapViewModel: MapViewModel by viewModels()
    private val slowMarker = mutableListOf<MapCluster>()
    private val allMarker = mutableListOf<MapCluster>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.inputs = mapViewModel.inputs
        gMap = binding.mapview
        gMap.onCreate(savedInstanceState)
        gMap.getMapAsync(this)
        return binding.root
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.searchFilterState.collect {
                    if (it is MainSearchFilterState.Main) {
                        binding.searchFilterEntity = it.searchFilters
                        it.searchFilters.let { entity ->
                            if (entity.combo) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_COMBO)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_COMBO)
                                )
                            }
                            if (entity.demo) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_DEMO)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_DEMO)
                                )
                            }
                            if (entity.ac) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_AC)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_AC)
                                )
                            }
                            if (entity.slow) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerArray().filter { cluster ->
                                        cluster.chargeTp == CHARGER_TYPE_SLOW
                                    }
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerArray().filter { cluster ->
                                        cluster.chargeTp == CHARGER_TYPE_SLOW
                                    }
                                )
                            }
                        }
                        clusterManager.cluster()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (activity as MainActivity)
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        gMap.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        initMap()
        initCluster()
        observeChargerInfoState()
        observeUIState()
        observeUIEffect()
        observeChargerDetailState()
        observeSearchData()
    }


    @SuppressLint("MissingPermission")
    private fun initMap() {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapViewModel.updateNowLocation(), MapConstants.DEFAULT_ZOOM
            )
        )
        mMap.setOnMapClickListener(this)
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.isMyLocationEnabled = true
    }

    private fun initCluster() {
        clusterManager = MyClusterManager(requireContext(), mMap, mapViewModel)
        mMap.setOnMarkerClickListener(clusterManager)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setInfoWindowAdapter(clusterManager.markerManager)
    }

    private fun observeChargerDetailState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.chargerDetailState.collect {
                    if (it is MainChargerDetailState.Main) {
                        binding.chargeDetailEntity = it.chargerDetail
                    }
                }
            }
        }
    }

    private fun observeUIEffect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.mainEffects.collect {
                    when (it) {
                        is MainEffect.MoveCamera -> {
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    it.position,
                                    it.zoom
                                )
                            )
                        }
                        is MainEffect.SearchText -> {
                            binding.etInputAddress.clearFocus()
                            mapViewModel.updateSearchData(it.text)
                        }
                        is MainEffect.ShowDetail -> {
                            clusterManager.clearItems()
                            mapViewModel.apply {
                                clearKoreaAddress()
                                clearChargerMarkerArray()
                            }
                            val intent = Intent(activity, InfoActivity::class.java)
                            intent.putExtra("address", it.chargerDetail.markerInfo.addr)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

    }

    private fun observeSearchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.searchEvent.collect() {
                    it.let {
                        clusterManager.clearItems()
                        mapViewModel.apply {
                            clearKoreaAddress()
                            clearChargerMarkerArray()
                            setMarkerArray(it)
                        }
                        clusterManager.addItems(mapViewModel.getMarkerArray())
                        mapViewModel.getMarkerArray().forEach {
                            allMarker.add(it)
                        }
                        val location = LatLng(
                            calculateLocation(it, "lat"),
                            calculateLocation(it, "lon")
                        )
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                location, MapConstants.SEARCH_ZOOM
                            )
                        )
                        clusterManager.cluster()
                    }
                }
            }
        }
    }

    private fun calculateLocation(list: List<ChargerInfo>, type: String): Double {
        var result = 0.0
        list.forEach {
            when (type) {
                "lat" -> {
                    result += it.lat.toDouble()
                }
                else -> {
                    result += it.longi.toDouble()
                }
            }
        }
        result /= list.size
        return result
    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.chargerInfoState.collect() {
                    if (it is MainChargerInfoState.Main) {
                        it.chargerInfo.let { list ->
                            mapViewModel.setMarkerArray(list)

                            mapViewModel.getMarkerArray().forEach { data ->
                                if (data.chargeTp != MapConstants.CHARGER_TYPE_SLOW)
                                    clusterManager.addItem(data)
                            }
                            //
                            mapViewModel.getMarkerArray().forEach { data ->
                                allMarker.add(data)
                            }
                            //
                            clusterManager.cluster()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        gMap.onStop()
    }

    override fun onResume() {
        super.onResume()
        gMap.onResume()
    }

    override fun onPause() {
        /*clusterManager.clearItems()
        allMarker.clear()
        mapViewModel.clearKoreaAddress()
        mapViewModel.clearChargerMarkerArray()*/
        super.onPause()
        gMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        gMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        gMap.onLowMemory()
    }

    override fun onMyLocationButtonClick(): Boolean {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapViewModel.updateNowLocation(), MapConstants.DEFAULT_ZOOM
            )
        )
        return false
    }

    override fun onMapClick(p0: LatLng) {
        mapViewModel.onMarkerClick(visible = false)
    }
}

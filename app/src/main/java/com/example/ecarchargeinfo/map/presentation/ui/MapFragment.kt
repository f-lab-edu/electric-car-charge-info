package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.FragmentMapBinding
import com.example.ecarchargeinfo.info.presentation.ui.InfoActivity
import com.example.ecarchargeinfo.main.presentation.output.MainChargerDetailState
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.main.presentation.output.MainSearchFilterState
import com.example.ecarchargeinfo.main.presentation.ui.MainActivity
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_AC
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_COMBO
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_DEMO
import com.example.ecarchargeinfo.map.domain.model.MapConstants.CHARGER_TYPE_SLOW
import com.example.ecarchargeinfo.map.domain.util.ClusterRenderer
import com.example.ecarchargeinfo.map.domain.util.MyClusterManager
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.map.presentation.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
                    GoogleMap.OnMapClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var mainActivity: MainActivity? = null
    private lateinit var clusterManager: MyClusterManager<MyItem>

    @Inject
    lateinit var mapViewModel: MapViewModel
    private val slowMarker = ArrayList<MyItem>()
    private val allMarker = ArrayList<MyItem>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.inputs = mapViewModel.inputs
        binding.lifecycleOwner = this
        gMap = binding.mapview
        gMap.onCreate(savedInstanceState)
        gMap.getMapAsync(this)

        binding.btnDetail?.setOnClickListener {
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("address", binding.tvDetailAddr?.text.toString())
            intent.putExtra("lat", mapViewModel.updateNowLocation().latitude.toString())
            intent.putExtra("lon", mapViewModel.updateNowLocation().longitude.toString())
            startActivity(intent)
        }
        return binding.root
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.searchFilterState.collect {
                    if (it is MainSearchFilterState.Main) {
                        binding.searchFilterEntity = it.searchFilters
                        it.searchFilters.let {
                            if (it.combo) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_COMBO)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_COMBO)
                                )
                            }
                            if (it.demo) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_DEMO)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_DEMO)
                                )
                            }
                            if (it.ac) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_AC)
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerByFiltered(CHARGER_TYPE_AC)
                                )
                            }
                            if (it.slow) {
                                clusterManager.addItems(
                                    mapViewModel.getMarkerArray().stream().filter { it ->
                                        it.getChargeTp() == CHARGER_TYPE_SLOW
                                    }.collect(Collectors.toList())
                                )
                            } else {
                                clusterManager.removeItems(
                                    mapViewModel.getMarkerArray().stream().filter { it ->
                                        it.getChargeTp() == CHARGER_TYPE_SLOW
                                    }.collect(Collectors.toList())
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val nowLocationMarker = MarkerOptions().position(mapViewModel.updateNowLocation())
            .title(resources.getString(R.string.now_location))
        mMap.addMarker(nowLocationMarker)?.showInfoWindow()
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapViewModel.updateNowLocation(), MapConstants.DEFAULT_ZOOM
            )
        )
        mMap.isMyLocationEnabled = true
        initCluster()
        observeChargerDetailState()
        observeChargerInfoState()
        observeGeocoder()
        observeUIState()
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.setOnMapClickListener(this)
    }

    private fun initCluster() {
        clusterManager = MyClusterManager(requireContext(), mMap, this)
        clusterManager.renderer = ClusterRenderer(requireContext(), mMap, clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        mMap.setOnCameraIdleListener(clusterManager)

        clusterManager.setOnClusterClickListener {
            val clusterLocation = LatLng(it.position.latitude, it.position.longitude)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    clusterLocation, MapConstants.CLUSTER_ZOOM
                )
            )
            return@setOnClusterClickListener false
        }


        clusterManager.setOnClusterItemClickListener {
            val itemLocation = LatLng(it.position.latitude, it.position.longitude)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    itemLocation, MapConstants.MARKER_ZOOM
                )
            )
            mapViewModel.onMarkerClick(
                visible = true, MarkerInfo(
                    it.title, it.getAddr(), it.getChargeTp(), it.snippet
                )
            )
            return@setOnClusterItemClickListener false
        }
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

    private fun observeGeocoder() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.geocoderEvent.collect() {
                    if (it != "") mapViewModel.updateChargerInfo(it)
                }
            }
        }
    }

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.chargerInfoState.collect() {
                    if (it is MainChargerInfoState.Main) {
                        it.chargerInfo.let {
                            mapViewModel.setMarkerArray(it)
                            clusterManager.addItems(mapViewModel.getMarkerArray())
                            //
                            mapViewModel.getMarkerArray().forEach {
                                allMarker.add(it)
                            }
                            //
                            clusterManager.cluster()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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
        clusterManager.clearItems()
        allMarker.clear()
        mapViewModel.clearKoreaAddress()
        mapViewModel.clearChargerMarkerArray()
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
        return true
    }

    override fun onMapClick(p0: LatLng) {
        mapViewModel.onMarkerClick(visible = false)
    }
}

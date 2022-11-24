package com.example.ecarchargeinfo.map.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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
import com.example.ecarchargeinfo.map.domain.model.MapConstants.IMAGE_HEIGHT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.IMAGE_WIDTH
import com.example.ecarchargeinfo.map.domain.util.ClusterRenderer
import com.example.ecarchargeinfo.map.domain.util.MyClusterManager
import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.map.presentation.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapClickListener {
    private lateinit var gMap: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var mainActivity: MainActivity? = null
    private lateinit var clusterManager: MyClusterManager<MyItem>

    @Inject
    lateinit var mapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.inputs = mapViewModel.inputs
        binding.lifecycleOwner = this
        gMap = binding.mapview
        gMap.onCreate(savedInstanceState)
        gMap.getMapAsync(this)
        binding.btnDetail?.setOnClickListener {
            clusterManager.removeItems(allMarker)
            clusterManager.removeItems(demoMarker)
            clusterManager.removeItems(comboMarker)
            clusterManager.removeItems(acMarker)
            clusterManager.cluster()
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("address", binding.tvDetailAddr?.text.toString())
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
                                clusterManager.addItems(comboMarker)

                            } else {
                                allMarker.forEach {
                                    if (it.getCptp() == CHARGER_TYPE_COMBO) {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(comboMarker)
                            }

                            if (it.demo) {
                                clusterManager.addItems(demoMarker)
                            } else {
                                allMarker.forEach {
                                    if (it.getCptp() == CHARGER_TYPE_DEMO) {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(demoMarker)
                            }

                            if (it.ac) {
                                clusterManager.addItems(acMarker)
                            } else {
                                allMarker.forEach {
                                    if (it.getCptp() == CHARGER_TYPE_AC) {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(acMarker)
                            }

                            if (it.slow) {
                                clusterManager.addItems(slowMarker)
                            } else {
                                allMarker.forEach {
                                    if (it.getChargeTp() == CHARGER_TYPE_SLOW) {
                                        slowMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(slowMarker)
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
        val nowLocation = mapViewModel.updateNowLocation()
        val nowLocationMarker = MarkerOptions().position(nowLocation)
            .title(resources.getString(R.string.now_location))
        mMap.addMarker(nowLocationMarker)?.showInfoWindow()
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                nowLocation,
                MapConstants.DEFAULT_ZOOM
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

    fun initCluster() {
        clusterManager = MyClusterManager(requireContext(), mMap, this)
        clusterManager.renderer = ClusterRenderer(requireContext(), mMap, clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        mMap.setOnCameraIdleListener(clusterManager)

        clusterManager.setOnClusterClickListener {
            val clusterLocation = LatLng(it.position.latitude, it.position.longitude)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    clusterLocation,
                    MapConstants.CLUSTER_ZOOM
                )
            )
            return@setOnClusterClickListener false
        }

        clusterManager.setOnClusterItemClickListener {
            val itemLocation = LatLng(it.position.latitude, it.position.longitude)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    itemLocation,
                    MapConstants.MARKER_ZOOM
                )
            )
            mapViewModel.onMarkerClick(
                visible = true,
                MarkerInfo(
                    it.title,
                    it.getAddr(),
                    it.getChargeTp(),
                    it.snippet
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
                    if (it != "")
                        mapViewModel.updateChargerInfo(it)
                }
            }
        }
    }

    private val comboMarker = ArrayList<MyItem>()
    private val demoMarker = ArrayList<MyItem>()
    private val acMarker = ArrayList<MyItem>()
    private val slowMarker = ArrayList<MyItem>()
    private val allMarker = mutableSetOf<MyItem>()

    private fun observeChargerInfoState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapViewModel.outputs.chargerInfoState.collect() {
                    if (it is MainChargerInfoState.Main) {
                        it.chargerInfo.let {
                            it.forEachIndexed { index, data ->
                                if (index > 0) {
                                    if (it[index - 1].csNm == data.csNm)
                                        return@forEachIndexed
                                }

                                val location = LatLng(data.lat.toDouble(), data.longi.toDouble())
                                val markerImage =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        when (data.chargeTp) {
                                            CHARGER_TYPE_SLOW -> R.drawable.volt_slow
                                            else -> R.drawable.volt
                                        },
                                        null
                                    ) as BitmapDrawable
                                val resizeImage =
                                    Bitmap.createScaledBitmap(
                                        markerImage.bitmap,
                                        IMAGE_WIDTH,
                                        IMAGE_HEIGHT,
                                        false
                                    )
                                val item = MyItem(
                                    location,
                                    data.csNm,
                                    data.cpStat,
                                    BitmapDescriptorFactory.fromBitmap((resizeImage)),
                                    data.addr,
                                    data.chargeTp,
                                    data.cpTp
                                )
                                allMarker.add(item)
                            }
                            clusterManager.addItems(allMarker)
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
        comboMarker.clear()
        demoMarker.clear()
        acMarker.clear()
        allMarker.clear()
        mapViewModel.clearKoreaAddress()
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
                mapViewModel.updateNowLocation(),
                MapConstants.DEFAULT_ZOOM
            )
        )
        return true
    }

    override fun onMapClick(p0: LatLng) {
        mapViewModel.onMarkerClick(visible = false)
    }
}

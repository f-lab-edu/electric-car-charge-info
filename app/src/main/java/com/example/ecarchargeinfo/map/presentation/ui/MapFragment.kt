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
                                allMarkerTest.forEach {
                                    if (it.getCptp() == "7")    {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(comboMarker)
                            }

                            if (it.demo) {
                                clusterManager.addItems(demoMarker)
                            } else {
                                allMarkerTest.forEach {
                                    if (it.getCptp() == "5")    {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(demoMarker)
                            }

                            if (it.ac) {
                                clusterManager.addItems(acMarker)
                            } else {
                                allMarkerTest.forEach {
                                    if (it.getCptp() == "6")    {
                                        comboMarker.add(it)
                                    }
                                }
                                clusterManager.removeItems(acMarker)
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
                    MapConstants.DEFAULT_ZOOM
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

    private val allMarker = ArrayList<MyItem>()
    private val comboMarker = ArrayList<MyItem>()
    private val demoMarker = ArrayList<MyItem>()
    private val acMarker = ArrayList<MyItem>()
    private val allMarkerTest = mutableSetOf<MyItem>()

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
                                            "1" -> R.drawable.volt_slow
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
                                allMarkerTest.add(item)


                                /*allMarkerTest.forEach {
                                    if (it.title == data.csNm)
                                        return@forEachIndexed
                                }
                                val location = LatLng(data.lat.toDouble(), data.longi.toDouble())
                                val markerImage =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        when (data.chargeTp) {
                                            "1" -> R.drawable.volt_slow
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
                                    data.chargeTp
                                )

                                when (data.cpTp) {
                                    "7" -> {
                                        if (!comboMarker.contains(item)) {
                                            comboMarker.add(item)
                                        }
                                    }

                                    "5" -> {
                                        if (!demoMarker.contains(item)) {
                                            demoMarker.add(item)
                                        }
                                    }

                                    "6" -> {
                                        if (!acMarker.contains(item)) {
                                            acMarker.add(item)
                                        }
                                    }

                                    else -> {
                                        if (!allMarker.contains(item)) {
                                            allMarker.add(item)
                                        }
                                    }
                                }*/
                            }
                            allMarkerTest.forEach {
                                println("@@@@" + it.title)
                                println("@@" + it.getAddr())
                            }
                            clusterManager.addItems(allMarkerTest)
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
        allMarkerTest.clear()
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

    /*   override fun onInfoWindowClick(marker: Marker) {

           val intent = Intent(activity, InfoActivity::class.java)
           intent.putExtra("address", marker.title)
           startActivity(intent)
       }*/
}

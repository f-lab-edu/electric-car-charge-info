package com.example.ecarchargeinfo.view.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.slider.RangeSlider


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var viewModel: MainViewModel
    private var userLocation: Location? = null
    private lateinit var mapFragment: SupportMapFragment
    private var REQUEST_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        checkPermission()
        getLocation()

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btn1.setOnClickListener {
            getLocation()
            mapFragment.getMapAsync(this)
        }

        binding.sliderChargeSpeed.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                binding.tvSlider.text = slider.values.toString()
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                binding.tvSlider.text = slider.values.toString()
            }

        })
        binding.sliderChargeSpeed.addOnChangeListener{ slider, value, fromUser ->
            val values = slider.values

        }
    }
    
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        userLocation = getLatLng()
    }

    private fun getLatLng(): Location? {
        var currentLatLng: Location? = null
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            currentLatLng = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return currentLatLng
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSION, 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.size == REQUEST_PERMISSION.size) {
            var checkResult = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }
            if (checkResult) {

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUEST_PERMISSION[0]
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, REQUEST_PERMISSION[1])
                ) {
                    Toast.makeText(this, "권한이 거부되었습니다. 다시실행해주세요", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "권한이 거부되었습니다. 설정에서 허용해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        if (p0 == null) {
            if (userLocation != null) {
                mMap = p0
                val nowLocation = LatLng(userLocation!!.latitude, userLocation!!.longitude)
                mMap.addMarker(MarkerOptions().position(nowLocation!!).title("현 위치"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowLocation!!, 15f))
            }
        }
    }
}
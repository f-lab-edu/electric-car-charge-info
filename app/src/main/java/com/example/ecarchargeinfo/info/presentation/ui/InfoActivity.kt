package com.example.ecarchargeinfo.info.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.databinding.ActivityInfoBinding
import com.example.ecarchargeinfo.info.presentation.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    @Inject lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        val test = intent.getStringExtra("address")
        binding.tvTitle.text = test
        binding.tvAddress.text = test

        viewModel.test()

    }
}

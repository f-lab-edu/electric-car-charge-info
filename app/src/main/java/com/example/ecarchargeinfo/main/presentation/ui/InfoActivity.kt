package com.example.ecarchargeinfo.main.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecarchargeinfo.R

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val test = intent.getStringExtra("address")
        println(test+"@@@@@")
    }
}
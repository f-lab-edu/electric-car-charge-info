package com.example.ecarchargeinfo.config

import android.app.Application
import com.example.ecarchargeinfo.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    val API_URL = "https://api.odcloud.kr/"
        companion object    {
        lateinit var sRetrofit: Retrofit
        lateinit var API_KEY: String
    }

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        API_KEY = resources.getString(R.string.ev_charger_key)
    }

    private fun initRetrofit()  {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

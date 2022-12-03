package com.example.ecarchargeinfo.config.di

import android.content.Context
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import com.example.ecarchargeinfo.retrofit.ChargerInfoApi
import com.example.ecarchargeinfo.retrofit.GeoCoderApi
import com.example.ecarchargeinfo.retrofit.NetworkConstants
import com.google.android.gms.maps.GoogleMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideGeoCoderApi(retrofitBuilder: Retrofit.Builder): GeoCoderApi {
        return retrofitBuilder
            .baseUrl(NetworkConstants.NAVER_GEOCODING_URL)
            .build()
            .create(GeoCoderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChargerInfoApi(retrofitBuilder: Retrofit.Builder): ChargerInfoApi {
        return retrofitBuilder
            .baseUrl(NetworkConstants.CHARGER_INFO_URL)
            .build()
            .create(ChargerInfoApi::class.java)
    }
}

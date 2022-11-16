package com.example.ecarchargeinfo.config.di

import android.content.Context
import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.config.model.ApplicationConstants.BASE_URL
import com.example.ecarchargeinfo.retrofit.ChargerInfoApi
import com.example.ecarchargeinfo.retrofit.GeoCoderApi
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
    fun provideGoogleMap(@ApplicationContext googleMap: GoogleMap) = googleMap

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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    

    @Singleton
    @Provides
    fun provideGeoCoderApi(retrofit: Retrofit): GeoCoderApi {
        return retrofit.create(GeoCoderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChargerInfoApi(retrofit: Retrofit): ChargerInfoApi {
        return retrofit.create(ChargerInfoApi::class.java)
    }
}

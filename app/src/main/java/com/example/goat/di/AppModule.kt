package com.example.goat.di

import com.example.goat.common.BASE_URL
import com.example.goat.data.remote.api.GotqApi
import com.example.goat.data.repository.GotqDataSource
import com.example.goat.domain.repository.GotqRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

object AppModule {
    @Provides
    @Singleton
    fun provideGotqApi(): GotqApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(GotqApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGotqRepository(gotqApi: GotqApi): GotqRepository {
        return GotqDataSource(gotqApi)
    }
}
package com.whoismacy.android.marsrealestate.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.whoismacy.android.marsrealestate.data.network.ApiHelper
import com.whoismacy.android.marsrealestate.data.network.ApiHelperImpl
import com.whoismacy.android.marsrealestate.data.network.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper

    companion object {
        @Provides
        @Singleton
        fun provideMoshi(): Moshi =
            Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        @Provides
        @Singleton
        fun provideRetrofit(moshi: Moshi): Retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit
                .create(ApiService::class.java)
    }
}

package com.whoismacy.android.marsrealestate

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit by lazy {
    Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            Json
                .asConverterFactory("application/json; charset=utf-8".toMediaType()),
        ).build()
}

val apiService: ApiHelper by lazy {
    retrofit.create(ApiHelper::class.java)
}

package com.whoismacy.android.marsrealestate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.collections.emptyList

private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/realestate"

interface EstateApiHelper {
    @GET("/realestate")
    suspend fun getAllEstate(): Response<List<EstateSectionItem>>
}

private val retrofit by lazy {
    Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val apiService: EstateApiHelper by lazy {
    retrofit.create(EstateApiHelper::class.java)
}

suspend fun getAllEstates(): List<EstateSectionItem> {
    withContext(Dispatchers.IO) {
        val response = apiService.getAllEstate()
        if (response.isSuccessful) {
            val data = response.body()
            if (!data.isNullOrEmpty()) {
                return@withContext data
            }
        } else {
            return@withContext emptyList<EstateSectionItem>()
        }
    }
    return emptyList()
}

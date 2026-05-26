package com.whoismacy.android.marsrealestate.data.network

import com.whoismacy.android.marsrealestate.data.model.Estate
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/realestate")
    suspend fun getAllEstates(): Response<List<Estate>>
}

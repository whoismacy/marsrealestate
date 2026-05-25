package com.whoismacy.android.marsrealestate

import retrofit2.Response
import retrofit2.http.GET

interface ApiHelper {
    @GET("/realestate")
    suspend fun getAllEstates(): Response<List<Estate>>
}

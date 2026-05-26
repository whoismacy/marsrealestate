package com.whoismacy.android.marsrealestate.data.network

import com.whoismacy.android.marsrealestate.data.model.Estate
import retrofit2.Response

interface ApiHelper {
    suspend fun getAllEstates(): Response<List<Estate>>
}

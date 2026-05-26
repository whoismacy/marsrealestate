package com.whoismacy.android.marsrealestate.data.network

import com.whoismacy.android.marsrealestate.data.model.Estate
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl
    @Inject
    constructor(
        private val apiService: ApiService,
    ) : ApiHelper {
        override suspend fun getAllEstates(): Response<List<Estate>> =
            apiService
                .getAllEstates()
    }

package com.whoismacy.android.marsrealestate.data.repository

import com.whoismacy.android.marsrealestate.data.model.Estate
import com.whoismacy.android.marsrealestate.data.network.ApiHelper
import com.whoismacy.android.marsrealestate.utils.Result
import java.lang.reflect.Constructor
import javax.inject.Inject

class EstateRepository
    @Inject
    constructor(
        private val apiHelper: ApiHelper,
    ) {
        suspend fun fetchEstates(): Result<List<Estate>> =
            try {
                val res = apiHelper.getAllEstates()
                if (res.isSuccessful && res.body() != null) {
                    Result.Success(res.body()!!)
                } else {
                    Result.Error("Failed to fetch data")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error")
            }
    }

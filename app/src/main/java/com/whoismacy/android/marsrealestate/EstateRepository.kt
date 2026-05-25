package com.whoismacy.android.marsrealestate

class EstateRepository(
    private val apiService: ApiHelper,
) {
    suspend fun fetchEstates(): Result<List<Estate>> =
        try {
            val res = apiService.getAllEstates()
            if (res.isSuccessful && res.body() != null) {
                Result.Success(res.body()!!)
            } else {
                Result.Error("Failed to fetch data")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
}

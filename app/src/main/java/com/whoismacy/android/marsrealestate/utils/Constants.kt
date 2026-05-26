package com.whoismacy.android.marsrealestate.utils

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data class Error(
        val message: String,
    ) : UiState<Nothing>
}

sealed class Result<out T> {
    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val message: String,
        val exception: Exception? = null,
    ) : Result<Nothing>()
}

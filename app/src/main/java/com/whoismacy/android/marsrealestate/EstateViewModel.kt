package com.whoismacy.android.marsrealestate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@JsonClass(generateAdapter = true)
data class Estate(
    val id: String,
    val type: String,
    @param:Json(name = "image_src") val imageUrl: String? = null,
    val price: Int,
)

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

class EstateViewModel(
    private val repository: EstateRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Estate>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Estate>>> = _uiState.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = repository.fetchEstates()) {
                is Result.Success -> _uiState.value = UiState.Success(result.data)
                is Result.Error -> _uiState.value = UiState.Error(result.message)
            }
        }
    }
}

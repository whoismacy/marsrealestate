package com.whoismacy.android.marsrealestate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoismacy.android.marsrealestate.data.model.Estate
import com.whoismacy.android.marsrealestate.data.repository.EstateRepository
import com.whoismacy.android.marsrealestate.utils.Result
import com.whoismacy.android.marsrealestate.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateViewModel
    @Inject
    constructor(
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

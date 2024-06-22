package com.abc.stockmarket.presentation.companies.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.domain.repository.StockRepository
import com.abc.stockmarket.presentation.companies.event.CompaniesEvent
import com.abc.stockmarket.presentation.companies.state.CompaniesState
import com.abc.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val companiesRepository: StockRepository
) : ViewModel() {

    private val _navigateToCompanyInfo = MutableSharedFlow<CompanyListing>()
    val navigateToCompanyInfo = _navigateToCompanyInfo.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    var state by mutableStateOf(CompaniesState())

    private var searchJob: Job? = null

    init {
        getCompanies()
    }

    fun onEvent(event: CompaniesEvent) = viewModelScope.launch {
        when (event) {
            is CompaniesEvent.Refresh -> {
                getCompanies(fetchFromRemote = true)
            }

            is CompaniesEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                handleOnSearchQueryChange()
            }

            is CompaniesEvent.OnItemClicked -> {
                _navigateToCompanyInfo.emit(state.companies[event.position])
            }
        }
    }

    private fun handleOnSearchQueryChange() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getCompanies()
        }
    }

    private fun getCompanies(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            companiesRepository.getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                companies = result.data ?: emptyList()
                            )
                        }

                        is Resource.Error -> {
                            _error.emit(result.message ?: "Unknown error")
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}
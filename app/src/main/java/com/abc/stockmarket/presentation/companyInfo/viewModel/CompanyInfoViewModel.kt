package com.abc.stockmarket.presentation.companyInfo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.abc.stockmarket.domain.model.CompanyInfo
import com.abc.stockmarket.domain.repository.StockRepository
import com.abc.stockmarket.presentation.companyInfo.event.CompanyInfoEvent
import com.abc.stockmarket.presentation.companyInfo.state.CompanyInfoState
import com.abc.stockmarket.presentation.companyInfo.view.CompanyInfoScreen
import com.abc.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companiesRepository: StockRepository
) : ViewModel() {

    private val _company = savedStateHandle.toRoute<CompanyInfoScreen>().company

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    var state by mutableStateOf(CompanyInfoState())

    init {
        state = state.copy(company = _company)
        getCompanyInfo()
    }

    fun onEvent(event: CompanyInfoEvent) = viewModelScope.launch {
        when (event) {
            is CompanyInfoEvent.OnBottomSheetDismiss -> {

            }
        }
    }

    private fun getCompanyInfo() {
        viewModelScope.launch {

            companiesRepository.getCompanyInfo(_company.symbol)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                companyInfo = result.data!!,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _error.emit(result.message ?: "Unknown error")
                            state = state.copy(isLoading = false)
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }

}
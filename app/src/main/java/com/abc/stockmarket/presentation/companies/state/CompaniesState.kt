package com.abc.stockmarket.presentation.companies.state

import com.abc.stockmarket.domain.model.CompanyListing

data class CompaniesState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)

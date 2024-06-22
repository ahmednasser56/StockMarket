package com.abc.stockmarket.presentation.companies.event

sealed class CompaniesEvent {
    data object Refresh: CompaniesEvent()
    data class OnSearchQueryChange(val query: String): CompaniesEvent()
    data class OnItemClicked(val position: Int): CompaniesEvent()
}
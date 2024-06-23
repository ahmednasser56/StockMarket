package com.abc.stockmarket.presentation.common.navigation

import com.abc.stockmarket.domain.model.CompanyListing
import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object CompaniesScreen

    @Serializable
    data class CompanyInfoScreen(
        val company: CompanyListing
    )
}
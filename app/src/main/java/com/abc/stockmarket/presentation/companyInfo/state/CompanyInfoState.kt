package com.abc.stockmarket.presentation.companyInfo.state

import com.abc.stockmarket.domain.model.CompanyInfo
import com.abc.stockmarket.domain.model.CompanyListing

data class CompanyInfoState(
    val companyInfo: CompanyInfo = CompanyInfo(),
    val isLoading: Boolean = false
)

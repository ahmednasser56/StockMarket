package com.abc.stockmarket.presentation.companyInfo.event

sealed class CompanyInfoEvent {
    data object OnBottomSheetDismiss: CompanyInfoEvent()
}
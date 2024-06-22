package com.abc.stockmarket.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CompanyListing(
    val name: String = "",
    val symbol: String = "",
    val exchange: String = "",
) : Parcelable

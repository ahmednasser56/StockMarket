package com.abc.stockmarket.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @field:SerializedName("Symbol") val symbol: String?,
    @field:SerializedName("Description") val description: String?,
    @field:SerializedName("Name") val name: String?,
    @field:SerializedName("Country") val country: String?,
    @field:SerializedName("Industry") val industry: String?,
)

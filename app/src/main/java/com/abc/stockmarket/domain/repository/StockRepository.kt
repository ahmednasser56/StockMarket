package com.abc.stockmarket.domain.repository

import com.abc.stockmarket.domain.model.CompanyInfo
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Flow<Resource<CompanyInfo>>
}
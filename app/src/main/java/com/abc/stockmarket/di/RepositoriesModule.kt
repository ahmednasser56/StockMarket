package com.abc.stockmarket.di

import com.abc.stockmarket.data.csv.CSVParser
import com.abc.stockmarket.data.csv.CompanyListingsParser
import com.abc.stockmarket.data.repository.StockRepositoryImpl
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
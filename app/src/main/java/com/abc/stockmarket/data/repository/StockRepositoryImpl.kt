package com.abc.stockmarket.data.repository

import com.abc.stockmarket.data.csv.CSVParser
import com.abc.stockmarket.data.local.StockDatabase
import com.abc.stockmarket.data.mapper.toCompanyInfo
import com.abc.stockmarket.data.mapper.toCompanyListing
import com.abc.stockmarket.data.mapper.toCompanyListingEntity
import com.abc.stockmarket.data.remote.StockApi
import com.abc.stockmarket.domain.model.CompanyInfo
import com.abc.stockmarket.domain.model.CompanyListing
import com.abc.stockmarket.domain.repository.StockRepository
import com.abc.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val result = api.getCompanyInfo(symbol)
                emit(Resource.Success(result.toCompanyInfo()))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load company info"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load company info"))
            }
        }
    }
}
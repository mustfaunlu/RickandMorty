package com.unludev.rickandmorty.data.datasource.location

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.api.RickAndMortyApi
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.data.model.location.LocationList
import com.unludev.rickandmorty.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRemoteDataSourceImp @Inject constructor(
    private val api: RickAndMortyApi,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher = Dispatchers.IO,
) : LocationRemoteDataSource {
    override suspend fun getLocations(page: Int): NetworkResponse<LocationList> {
        return try {
            val response = withContext(iODispatcher) { api.getLocations(page) }
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getLocation(id: Int): NetworkResponse<Location> {
        return try {
            val response = withContext(iODispatcher) { api.getLocation(id) }
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}

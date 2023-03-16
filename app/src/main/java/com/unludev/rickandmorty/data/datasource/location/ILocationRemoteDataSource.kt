package com.unludev.rickandmorty.data.datasource.location

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.api.RickAndMortyApi
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.data.model.location.LocationList
import javax.inject.Inject

class ILocationRemoteDataSource @Inject constructor(private val api: RickAndMortyApi) : LocationRemoteDataSource{
    override suspend fun getLocations(): NetworkResponse<LocationList> {
        return try {
            val response = api.getLocations()
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getLocation(id: Int): NetworkResponse<Location> {
        return try {
            val response = api.getLocation(id)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

}
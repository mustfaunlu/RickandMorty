package com.unludev.rickandmorty.data.datasource.location

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.data.model.location.LocationList

interface LocationRemoteDataSource {
    suspend fun getLocations(page: Int = 1): NetworkResponse<LocationList>
    suspend fun getLocation(id: Int): NetworkResponse<Location>
}

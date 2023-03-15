package com.unludev.rickandmorty.data.repository.location

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.data.model.location.LocationList
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(): Flow<NetworkResponse<LocationList>>
    suspend fun getLocation(id: Int): Flow<NetworkResponse<Location>>
}

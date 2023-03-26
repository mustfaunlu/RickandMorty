package com.unludev.rickandmorty.data.datasource.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.api.RickAndMortyApi
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRemoteDataSourceImp @Inject constructor(
    private val api: RickAndMortyApi,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    CharacterRemoteDataSource {
    override suspend fun getCharactersByIds(ids: String): NetworkResponse<List<RickAndMortyCharacter>> {
        return try {
            val response = api.getCharactersByIds(ids)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<RickAndMortyCharacter> {
        return try {
            val response = withContext(iODispatcher) { api.getSingleCharacter(id) }
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}

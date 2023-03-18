package com.unludev.rickandmorty.data.repository.character

import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.datasource.character.CharacterRemoteDataSource
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ICharacterRepository @Inject constructor(
    private val characterDataSource: CharacterRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CharacterRepository {
    override suspend fun getCharacters(ids: String): Flow<NetworkResponse<List<RickAndMortyCharacter>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = withContext(ioDispatcher) {
                try {
                    characterDataSource.getCharacters(ids)
                } catch (e: Exception) {
                    NetworkResponse.Error(e)
                }
            }
            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }
    }

    override suspend fun getAllCharacters(): Flow<NetworkResponse<CharacterList>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = withContext(ioDispatcher) {
                try {
                    characterDataSource.getAllCharacters()
                } catch (e: Exception) {
                    NetworkResponse.Error(e)
                }
            }
            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }
    }
}

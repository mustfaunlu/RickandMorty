package com.unludev.rickandmorty.data.repository.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.datasource.character.CharacterRemoteDataSource
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ICharacterRepository @Inject constructor(
    private val characterDataSource: CharacterRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CharacterRepository {
    override suspend fun getCharactersByIds(ids: String): Flow<NetworkResponse<List<RickAndMortyCharacter>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = try {
                characterDataSource.getCharactersByIds(ids)
            } catch (e: Exception) {
                NetworkResponse.Error(e)
            }
            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAllCharacters(): Flow<NetworkResponse<CharacterList>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = try {
                characterDataSource.getAllCharacters()
            } catch (e: Exception) {
                NetworkResponse.Error(e)
            }

            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getSingleCharacter(id: Int): Flow<NetworkResponse<RickAndMortyCharacter>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = try {
                characterDataSource.getSingleCharacter(id)
            } catch (e: Exception) {
                NetworkResponse.Error(e)
            }
            when (response) {
                is NetworkResponse.Success -> emit(NetworkResponse.Success(response.result))
                is NetworkResponse.Error -> emit(NetworkResponse.Error(response.exception))
                NetworkResponse.Loading -> Unit
            }
        }.flowOn(ioDispatcher)
    }
}

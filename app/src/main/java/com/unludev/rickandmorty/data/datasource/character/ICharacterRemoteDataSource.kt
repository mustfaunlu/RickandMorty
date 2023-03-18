package com.unludev.rickandmorty.data.datasource.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.api.RickAndMortyApi
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import javax.inject.Inject

class ICharacterRemoteDataSource @Inject constructor(private val api: RickAndMortyApi) : CharacterRemoteDataSource {
    override suspend fun getCharacters(ids: String): NetworkResponse<List<RickAndMortyCharacter>> {
        return try {
            val response = api.getCharacters(ids)
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getAllCharacters(): NetworkResponse<CharacterList> {
        return try {
            val response = api.getAllCharacters()
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}

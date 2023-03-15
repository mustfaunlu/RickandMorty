package com.unludev.rickandmorty.data.repository.character

import RickAndMortyCharacter
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.CharacterList
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(ids: String): Flow<NetworkResponse<List<RickAndMortyCharacter>>>

    suspend fun getAllCharacters(): Flow<NetworkResponse<CharacterList>>
}

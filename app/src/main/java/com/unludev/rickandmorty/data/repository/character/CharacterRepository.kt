package com.unludev.rickandmorty.data.repository.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharactersByIds(ids: String): Flow<NetworkResponse<List<RickAndMortyCharacter>>>
    suspend fun getSingleCharacter(id: Int): Flow<NetworkResponse<RickAndMortyCharacter>>
}

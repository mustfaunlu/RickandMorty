package com.unludev.rickandmorty.data.datasource.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter

interface CharacterRemoteDataSource {
    suspend fun getCharactersByIds(ids: String): NetworkResponse<List<RickAndMortyCharacter>>
    suspend fun getSingleCharacter(id: Int): NetworkResponse<RickAndMortyCharacter>
}

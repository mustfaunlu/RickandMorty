package com.unludev.rickandmorty.data.datasource.character

import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter

interface CharacterRemoteDataSource {
    suspend fun getCharacters(ids: String): NetworkResponse<List<RickAndMortyCharacter>>

    suspend fun getAllCharacters(): NetworkResponse<CharacterList>

    suspend fun getSingleCharacter(id: String): NetworkResponse<RickAndMortyCharacter>
}

package com.unludev.rickandmorty.data.datasource.character

import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.CharacterList

interface CharacterRemoteDataSource {
    suspend fun getCharacters(ids: String): NetworkResponse<List<RickAndMortyCharacter>>

    suspend fun getAllCharacters(): NetworkResponse<CharacterList>
}

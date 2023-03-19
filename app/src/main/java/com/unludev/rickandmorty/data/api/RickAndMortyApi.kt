package com.unludev.rickandmorty.data.api

import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.data.model.location.LocationList
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {

    @GET("location")
    suspend fun getLocations(): LocationList

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Location

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: String): List<RickAndMortyCharacter>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: String): RickAndMortyCharacter

    @GET("character")
    suspend fun getAllCharacters(): CharacterList
}

package com.unludev.rickandmorty.data.remote

import RickAndMortyCharacter
import com.unludev.rickandmorty.data.model.location.Location
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Location

    @GET("character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: String): List<RickAndMortyCharacter>

    @GET("character")
    suspend fun getRickAndMorty(): RickAndMortyCharacter
}

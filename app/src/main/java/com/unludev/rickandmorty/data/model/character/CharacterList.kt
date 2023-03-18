package com.unludev.rickandmorty.data.model.character

data class CharacterList(
    val info: Info,
    val results: List<RickAndMortyCharacter>,
)

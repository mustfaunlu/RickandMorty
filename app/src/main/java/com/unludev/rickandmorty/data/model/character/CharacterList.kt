package com.unludev.rickandmorty.data.model.character

import RickAndMortyCharacter

data class CharacterList(
    val info: Info,
    val results: List<RickAndMortyCharacter>,
)

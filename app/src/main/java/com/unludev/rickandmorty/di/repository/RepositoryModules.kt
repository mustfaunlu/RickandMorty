package com.unludev.rickandmorty.di.repository

import com.unludev.rickandmorty.data.repository.character.CharacterRepository
import com.unludev.rickandmorty.data.repository.character.ICharacterRepository
import com.unludev.rickandmorty.data.repository.location.ILocationRepository
import com.unludev.rickandmorty.data.repository.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CharacterRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCharacterRepository(characterRepository: ICharacterRepository): CharacterRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocationRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLocationRepository(locationRepository: ILocationRepository): LocationRepository
}

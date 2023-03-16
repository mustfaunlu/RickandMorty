package com.unludev.rickandmorty.di.datasource

import com.unludev.rickandmorty.data.datasource.character.CharacterRemoteDataSource
import com.unludev.rickandmorty.data.datasource.character.ICharacterRemoteDataSource
import com.unludev.rickandmorty.data.datasource.location.ILocationRemoteDataSource
import com.unludev.rickandmorty.data.datasource.location.LocationRemoteDataSource
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
    abstract fun bindCharacterDataSource(characterRemoteDataSource: ICharacterRemoteDataSource): CharacterRemoteDataSource
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocationRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLocationDataSource(locationRemoteDataSource: ILocationRemoteDataSource): LocationRemoteDataSource
}

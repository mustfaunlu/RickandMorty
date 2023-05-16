package com.unludev.rickandmorty.di.datasource

import com.unludev.rickandmorty.data.datasource.character.CharacterRemoteDataSource
import com.unludev.rickandmorty.data.datasource.character.CharacterRemoteDataSourceImp
import com.unludev.rickandmorty.data.datasource.location.LocationRemoteDataSource
import com.unludev.rickandmorty.data.datasource.location.LocationRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CharacterDatasourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCharacterDataSource(characterRemoteDataSource: CharacterRemoteDataSourceImp): CharacterRemoteDataSource
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocationDatasourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLocationDataSource(locationRemoteDataSource: LocationRemoteDataSourceImp): LocationRemoteDataSource
}

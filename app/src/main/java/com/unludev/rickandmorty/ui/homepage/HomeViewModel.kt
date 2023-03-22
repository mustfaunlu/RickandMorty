package com.unludev.rickandmorty.ui.homepage

import androidx.lifecycle.*
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.model.location.LocationList
import com.unludev.rickandmorty.data.repository.character.CharacterRepository
import com.unludev.rickandmorty.data.repository.location.LocationRepository
import com.unludev.rickandmorty.di.coroutine.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _charactersByIds = MutableLiveData<NetworkResponse<List<RickAndMortyCharacter>>>()
    val charactersByIds: LiveData<NetworkResponse<List<RickAndMortyCharacter>>> get() = _charactersByIds

    private val _characterList = MutableLiveData<NetworkResponse<CharacterList>>()
    val characterList: LiveData<NetworkResponse<CharacterList>> get() = _characterList

    private val _locationList = MutableLiveData<NetworkResponse<LocationList>>()
    val locationList: LiveData<NetworkResponse<LocationList>> get() = _locationList

    private val _singleCharacter = MutableLiveData<NetworkResponse<RickAndMortyCharacter>>()
    val singleCharacter: LiveData<NetworkResponse<RickAndMortyCharacter>> get() = _singleCharacter
    init {
        getLocations(0)
    }

    fun getCharactersById(ids: String) {
        viewModelScope.launch(ioDispatcher) {
            characterRepository.getCharactersByIds(ids).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _charactersByIds.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        _charactersByIds.postValue(NetworkResponse.Success(it.result))
                    }
                    is NetworkResponse.Error -> {
                        _charactersByIds.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }
    }

    fun getSingleCharacter(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            characterRepository.getSingleCharacter(id).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _singleCharacter.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        _singleCharacter.postValue(NetworkResponse.Success(it.result))
                    }
                    is NetworkResponse.Error -> {
                        _singleCharacter.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }
    }

    fun getAllCharacters() {
        viewModelScope.launch(ioDispatcher) {
            characterRepository.getAllCharacters().collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _characterList.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        _characterList.postValue(NetworkResponse.Success(it.result))
                    }
                    is NetworkResponse.Error -> {
                        _characterList.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }
    }

    fun getLocations(page: Int = 1) {
        viewModelScope.launch(ioDispatcher) {
            locationRepository.getLocations(page).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _locationList.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        _locationList.postValue(NetworkResponse.Success(it.result))
                    }
                    is NetworkResponse.Error -> {
                        _locationList.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }
    }
}

package com.unludev.rickandmorty.ui.homepage

import androidx.lifecycle.*
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.model.location.Location
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

    private val _singleCharacter = MutableLiveData<NetworkResponse<RickAndMortyCharacter>>()
    val singleCharacter: LiveData<NetworkResponse<RickAndMortyCharacter>> get() = _singleCharacter

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> get() = _progressBarVisibility

    var isLoading = false
    var locationsResultList = mutableListOf<Location>()

    init {
        getLocations(0)
    }

    private fun getCharactersById(ids: String) {
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

    private fun getSingleCharacter(id: Int) {
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

    fun getLocations(page: Int = 1) {
        viewModelScope.launch(ioDispatcher) {
            locationRepository.getLocations(page).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        isLoading = true
                        _progressBarVisibility.postValue(2) // visible and loading
                    }
                    is NetworkResponse.Success -> {
                        isLoading = false
                        if (locationsResultList == it.result!!.results) {
                            _progressBarVisibility.postValue(0) // gone and no more data
                            return@collect
                        } else {
                            _progressBarVisibility.postValue(1) // 3000 ms delay visible and data
                        }
                        locationsResultList.addAll(it.result.results)
                    }
                    is NetworkResponse.Error -> {
                        isLoading = false
                        _progressBarVisibility.postValue(-1) // visible and error
                    }
                }
            }
        }
    }

    fun clickLocation(location: Location) {
        val characterIds = location.residents.map { it.split("/").last() }
        if (characterIds.size > 1) {
            getCharactersById(characterIds.joinToString(","))
        } else {
            getSingleCharacter(characterIds[0].toInt())
        }
    }
}

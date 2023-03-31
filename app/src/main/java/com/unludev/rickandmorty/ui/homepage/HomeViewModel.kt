package com.unludev.rickandmorty.ui.homepage

import androidx.lifecycle.*
import com.unludev.rickandmorty.common.Constants.GONE_AND_NO_MORE_DATA
import com.unludev.rickandmorty.common.Constants.VISIBLE_AND_DELAYED
import com.unludev.rickandmorty.common.Constants.VISIBLE_AND_ERROR
import com.unludev.rickandmorty.common.Constants.VISIBLE_AND_LOADING
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
        getLocations()
    }

    private fun getCharactersById(ids: String) {
        viewModelScope.launch(ioDispatcher) {
            characterRepository.getCharactersByIds(ids).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _charactersByIds.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success -> {
                        if (it.result?.size == 1) {
                            _singleCharacter.postValue(NetworkResponse.Success(it.result[0]))
                        } else {
                            _charactersByIds.postValue(NetworkResponse.Success(it.result))
                        }
                    }
                    is NetworkResponse.Error -> {
                        _charactersByIds.postValue(NetworkResponse.Error(it.exception))
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
                        _progressBarVisibility.postValue(VISIBLE_AND_LOADING) // visible and loading
                    }
                    is NetworkResponse.Success -> {
                        isLoading = false
                        if (locationsResultList == it.result!!.results) {
                            _progressBarVisibility.postValue(GONE_AND_NO_MORE_DATA) // gone and no more data
                            return@collect
                        } else {
                            locationsResultList.addAll(it.result.results)
                            _progressBarVisibility.postValue(VISIBLE_AND_DELAYED) // 3000 ms delay visible and data
                        }
                    }
                    is NetworkResponse.Error -> {
                        isLoading = false
                        _progressBarVisibility.postValue(VISIBLE_AND_ERROR) // visible and error
                    }
                }
            }
        }
    }

    /**
     * If the location has only one resident, we will convert the character id firstly to list
     * then to string and get the character because the api doesn't accept single parameter
     * If the location has more than one resident, we will get the characters by ids
     */
    fun clickLocation(location: Location) {
        val characterIds = location.residents.map { it.split("/").last() }

        if (characterIds.size == 1) {
            getCharactersById(characterIds.joinToString(",").toList().toString())
        } else if (characterIds.isEmpty()) {
            _charactersByIds.postValue(NetworkResponse.Success(emptyList()))
        } else {
            getCharactersById(characterIds.joinToString(","))
        }
    }
}

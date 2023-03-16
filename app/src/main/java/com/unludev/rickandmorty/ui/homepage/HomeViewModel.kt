package com.unludev.rickandmorty.ui.homepage

import RickAndMortyCharacter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.CharacterList
import com.unludev.rickandmorty.data.model.location.LocationList
import com.unludev.rickandmorty.data.repository.character.CharacterRepository
import com.unludev.rickandmorty.data.repository.location.LocationRepository
import com.unludev.rickandmorty.utils.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    private val _characters = MutableLiveData<NetworkResponse<List<RickAndMortyCharacter>>>()
    val characters: LiveData<NetworkResponse<List<RickAndMortyCharacter>>> = _characters

    private val _characterList = MutableLiveData<NetworkResponse<CharacterList>>()
    val characterList: LiveData<NetworkResponse<CharacterList>> = _characterList

    private val _locationList = MutableLiveData<NetworkResponse<LocationList>>()
    val locationList: LiveData<NetworkResponse<LocationList>> = _locationList

    fun getCharactersById(ids: String) {
        viewModelScope.launch {
            characterRepository.getCharacters(ids).collect {
                when (it) {
                    is NetworkResponse.Loading -> _status.value = ApiStatus.LOADING
                    is NetworkResponse.Success -> {
                        _status.value = ApiStatus.DONE
                        _characters.value = it
                    }
                    is NetworkResponse.Error -> _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            characterRepository.getAllCharacters().collect {
                when (it) {
                    is NetworkResponse.Loading -> _status.value = ApiStatus.LOADING
                    is NetworkResponse.Success -> {
                        _status.value = ApiStatus.DONE
                        _characterList.value = it
                    }
                    is NetworkResponse.Error -> _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    fun getLocations() {
        viewModelScope.launch {
            locationRepository.getLocations().collect {
                when (it) {
                    is NetworkResponse.Loading -> _status.value = ApiStatus.LOADING
                    is NetworkResponse.Success -> {
                        _status.value = ApiStatus.DONE
                        _locationList.value = it
                    }
                    is NetworkResponse.Error -> _status.value = ApiStatus.ERROR
                }
            }
        }
    }
}

package com.unludev.rickandmorty.ui.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.unludev.rickandmorty.R
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.databinding.FragmentHomeBinding
import com.unludev.rickandmorty.utils.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var isLoading = false
    private var currentPage = 0
    private val maxPageSize = 7

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var characterAdapter: CharacterListAdapter
    private var resultsList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        addScrollListener()
        locationAdapter = LocationAdapter(resultsList, ::clickLocation)
        binding.locationRecyclerview.adapter = locationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        viewModel.locationList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    isLoading = false
                    val locations = response.result
                    if (resultsList.containsAll(locations?.results ?: emptyList())) { // TODO: fix this later
                        binding.progressBar.gone()
                        return@observe
                    }
                    resultsList.addAll(locations?.results ?: emptyList())
                    locationAdapter.notifyDataSetChanged()
                    binding.progressBar.postDelayed({
                        binding.progressBar.gone()
                    }, 3000)
                }
                is NetworkResponse.Loading -> {
                    isLoading = true
                    binding.progressBar.visible()
                }
                is NetworkResponse.Error -> {
                    isLoading = false
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                    binding.progressBar.visible()
                }
            }
        }

        viewModel.charactersByIds.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val characters = response.result
                    characterAdapter = CharacterListAdapter(::navigateToDetailFragment)
                    binding.characterRecyclerview.adapter = characterAdapter
                    characterAdapter.submitList(characters)
                }
                is NetworkResponse.Error -> {
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                }
                else -> {}
            }
        }

        viewModel.singleCharacter.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val character = response.result
                    characterAdapter = CharacterListAdapter(::navigateToDetailFragment)
                    binding.characterRecyclerview.adapter = characterAdapter
                    characterAdapter.submitList(listOf(character))
                }

                is NetworkResponse.Error -> {
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                }
                else -> {}
            }
        }
    }
    private fun navigateToDetailFragment(character: RickAndMortyCharacter) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
        findNavController().navigate(action)
    }
    private fun clickLocation(location: Location) {
        val characterIds = location.residents.map { it.split("/").last() }
        when (characterIds.size) {
            0, 1 -> {
                binding.characterRecyclerview.adapter =
                    null // TODO() -> geri tusuna basinca uygulamadan cikiyor
                binding.root.showSnack("No characters found")
            }
            else -> viewModel.getCharactersById(characterIds.joinToString(","))
        }
    }

    private fun addScrollListener() {
        binding.locationRecyclerview.addOnScrollListener(
            object :
                PaginationScrollListener(binding.locationRecyclerview.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.getLocations(++currentPage)
                }

                override fun isLastPage(): Boolean = currentPage == maxPageSize

                override fun isLoading(): Boolean = isLoading
            },
        )
    }
}

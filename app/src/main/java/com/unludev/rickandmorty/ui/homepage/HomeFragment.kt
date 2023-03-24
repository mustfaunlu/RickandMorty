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
import com.unludev.rickandmorty.databinding.FragmentHomeBinding
import com.unludev.rickandmorty.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var currentPage = 0
    private val maxPageSize = 7
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter
    private val characterAdapter by lazy {
        CharacterListAdapter(::navigateToDetailFragment)
    }

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
        locationAdapter = LocationAdapter(viewModel.locationsResultList, viewModel::clickLocation)
        binding.locationRecyclerview.adapter = locationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { progressBarVisibility ->
            when (progressBarVisibility) {
                0 -> {
                    if (viewModel.locationsResultList.isNotEmpty()) {
                        locationAdapter.notifyDataSetChanged()
                        binding.progressBar.gone()
                    } else {
                        binding.root.showSnack("No locations found")
                        binding.progressBar.gone()
                    }
                }
                1 -> {
                    if (viewModel.locationsResultList.isNotEmpty()) {
                        locationAdapter.notifyDataSetChanged()
                        binding.progressBar.postDelayed({
                            binding.progressBar.gone()
                        }, 3000)
                    } else {
                        binding.root.showSnack("No locations found")
                        binding.progressBar.gone()
                    }
                }
                2 -> {
                    binding.progressBar.visible()
                }
                -1 -> {
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                    binding.progressBar.visible()
                }
                else -> {
                    binding.root.showSnack("No locations found")
                }
            }
        }

        viewModel.singleCharacter.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val character = response.result
                    characterAdapter.submitList(mutableListOf(character))
                    if (characterAdapter.currentList.isEmpty()) {
                        binding.root.showSnack(getString(R.string.no_characters_found))
                        binding.progressBar.gone()
                    } else {
                        characterAdapter.submitList(mutableListOf(character))
                        binding.progressBar.gone()
                    }
                }

                is NetworkResponse.Error -> {
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                }
                else -> {}
            }
            binding.characterRecyclerview.adapter = characterAdapter
        }

        viewModel.charactersByIds.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val characters = response.result
                    if (characters!!.isEmpty()) {
                        characterAdapter.submitList(emptyList())
                        binding.root.showSnack(getString(R.string.no_characters_found))
                    } else {
                        characterAdapter.submitList(characters)
                    }
                    binding.progressBar.gone()
                }
                is NetworkResponse.Error -> {
                    binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                }
                is NetworkResponse.Loading -> {}
            }
            binding.characterRecyclerview.adapter = characterAdapter
        }
    }
    private fun navigateToDetailFragment(character: RickAndMortyCharacter) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
        findNavController().navigate(action)
    }

    private fun addScrollListener() {
        binding.locationRecyclerview.addOnScrollListener(
            object :
                PaginationScrollListener(binding.locationRecyclerview.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.getLocations(++currentPage)
                }

                override fun isLastPage(): Boolean = currentPage == maxPageSize

                override fun isLoading(): Boolean = viewModel.isLoading
            },
        )
    }
}

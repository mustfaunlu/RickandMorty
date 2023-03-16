package com.unludev.rickandmorty.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.unludev.rickandmorty.data.NetworkResponse
import com.unludev.rickandmorty.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var characterAdapter: CharacterListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLocations()

        viewModel.characters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val characters = response.result
                    characterAdapter = CharacterListAdapter(characters ?: emptyList()) { character ->
                        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(character)
                        findNavController().navigate(action)
                    }
                    binding.characterRecyclerview.adapter = characterAdapter
                }
                else -> {}
            }
        }

        viewModel.locationList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val locations = response.result
                    val adapter = LocationListAdapter(locations!!.results) { location ->
                        val characterIds = location.residents.map { it.split("/").last() }
                        viewModel.getCharactersById(characterIds.joinToString(","))
                    }
                    binding.apply {
                        locationRecyclerview.adapter = adapter
                    }
                }
                else -> {}
            }
        }
    }
}

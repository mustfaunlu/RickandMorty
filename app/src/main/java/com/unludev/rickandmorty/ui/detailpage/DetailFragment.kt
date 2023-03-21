package com.unludev.rickandmorty.ui.detailpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.unludev.rickandmorty.databinding.FragmentDetailBinding
import com.unludev.rickandmorty.utils.formatDate
import com.unludev.rickandmorty.utils.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = args.character.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailData(args)
    }

    private fun detailData(data: DetailFragmentArgs) {
        binding.apply {
            characterDetailPageImage.loadUrl(data.character.image)
            characterDetailPageGenderTxt.text = data.character.gender
            characterDetailPageSpecyTxt.text = data.character.species
            characterDetailPageStatusTxt.text = data.character.status
            characterDetailPageOriginTxt.text = data.character.origin.name
            characterDetailPageLocationTxt.text = data.character.location.name
            characterDetailPageCreatedTxt.text = formatDate(data.character.created)
            characterDetailPageEpisodeTxt.text = data.character.episode.joinToString(",") { it.split("/").last() }
        }
    }

    override fun onDestroyView() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Rick and Morty"
        super.onDestroyView()
        _binding = null
    }
}

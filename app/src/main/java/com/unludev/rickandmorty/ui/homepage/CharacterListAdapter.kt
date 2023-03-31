package com.unludev.rickandmorty.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unludev.rickandmorty.common.Constants.FEMALE_VIEW_TYPE
import com.unludev.rickandmorty.common.Constants.MALE_VIEW_TYPE
import com.unludev.rickandmorty.common.Constants.UNKNOWN_GENDERLESS_VIEW_TYPE
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.databinding.FemaleCharacterItemBinding
import com.unludev.rickandmorty.databinding.MaleCharacterItemBinding
import com.unludev.rickandmorty.databinding.UnknownGenderlessCharacterItemBinding
import com.unludev.rickandmorty.utils.loadUrl

class CharacterListAdapter(
    private val onItemClicked: (RickAndMortyCharacter) -> Unit,
) :
    ListAdapter<RickAndMortyCharacter, RecyclerView.ViewHolder>(
        AsyncDifferConfig.Builder(
            DiffCallback,
        ).build(),
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MALE_VIEW_TYPE -> {
                val maleBinding = MaleCharacterItemBinding.inflate(inflater, parent, false)
                MaleViewHolder(maleBinding)
            }
            FEMALE_VIEW_TYPE -> {
                val femaleBinding = FemaleCharacterItemBinding.inflate(inflater, parent, false)
                FemaleViewHolder(femaleBinding)
            }
            UNKNOWN_GENDERLESS_VIEW_TYPE -> {
                val unknownOrGenderlessBinding =
                    UnknownGenderlessCharacterItemBinding.inflate(inflater, parent, false)
                UnknownOrGenderlessViewHolder(unknownOrGenderlessBinding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = currentList[position]
        when (holder) {
            is MaleViewHolder -> holder.bind(character)
            is FemaleViewHolder -> holder.bind(character)
            is UnknownOrGenderlessViewHolder -> holder.bind(character)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].gender) {
            "Male" -> MALE_VIEW_TYPE
            "Female" -> FEMALE_VIEW_TYPE
            else -> UNKNOWN_GENDERLESS_VIEW_TYPE
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RickAndMortyCharacter>() {
        override fun areItemsTheSame(
            oldItem: RickAndMortyCharacter,
            newItem: RickAndMortyCharacter,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RickAndMortyCharacter,
            newItem: RickAndMortyCharacter,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class MaleViewHolder(private val binding: MaleCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: RickAndMortyCharacter) {
            binding.apply {
                characterName.text = character.name
                characterImage.loadUrl(character.image)
            }
            binding.root.setOnClickListener {
                onItemClicked(character)
            }
        }
    }

    inner class FemaleViewHolder(private val binding: FemaleCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: RickAndMortyCharacter) {
            binding.apply {
                characterName.text = character.name
                characterImage.loadUrl(character.image)
            }
            binding.root.setOnClickListener {
                onItemClicked(character)
            }
        }
    }

    inner class UnknownOrGenderlessViewHolder(private val binding: UnknownGenderlessCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: RickAndMortyCharacter) {
            binding.apply {
                characterName.text = character.name
                characterImage.loadUrl(character.image)
            }
            binding.root.setOnClickListener {
                onItemClicked(character)
            }
        }
    }
}

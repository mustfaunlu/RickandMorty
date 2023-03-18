package com.unludev.rickandmorty.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unludev.rickandmorty.data.model.character.RickAndMortyCharacter
import com.unludev.rickandmorty.databinding.FemaleCharacterItemBinding
import com.unludev.rickandmorty.databinding.MaleCharacterItemBinding
import com.unludev.rickandmorty.databinding.UnknownGenderlessCharacterItemBinding
import com.unludev.rickandmorty.utils.loadUrl

private const val MALE_VIEW_TYPE = 1
private const val FEMALE_VIEW_TYPE = 2
private const val UNKNOWN_GENDERLESS_VIEW_TYPE = 3
class CharacterListAdapter(private val characters: List<RickAndMortyCharacter>, private val onItemClicked: (RickAndMortyCharacter) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            MALE_VIEW_TYPE -> {
                val maleBinding = MaleCharacterItemBinding.inflate(inflater,parent, false)
                MaleViewHolder(maleBinding)
            }
            FEMALE_VIEW_TYPE -> {
                val femaleBinding = FemaleCharacterItemBinding.inflate(inflater,parent, false)
                FemaleViewHolder(femaleBinding)
            }
            UNKNOWN_GENDERLESS_VIEW_TYPE -> {
                val unknownOrGenderlessBinding = UnknownGenderlessCharacterItemBinding.inflate(inflater,parent, false)
                UnknownOrGenderlessViewHolder(unknownOrGenderlessBinding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]
        when(holder) {
            is MaleViewHolder -> holder.bind(character)
            is FemaleViewHolder -> holder.bind(character)
            is UnknownOrGenderlessViewHolder -> holder.bind(character)
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(characters[position].gender) {
            "Male" -> MALE_VIEW_TYPE
            "Female" -> FEMALE_VIEW_TYPE
            else -> UNKNOWN_GENDERLESS_VIEW_TYPE
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

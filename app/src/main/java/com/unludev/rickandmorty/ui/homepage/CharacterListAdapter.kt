package com.unludev.rickandmorty.ui.homepage

import RickAndMortyCharacter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unludev.rickandmorty.databinding.CharacterItemBinding
import com.unludev.rickandmorty.utils.loadUrl

class CharacterListAdapter(private val characters: List<RickAndMortyCharacter>, private val onItemClicked: (RickAndMortyCharacter) -> Unit) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class ViewHolder(private val binding: CharacterItemBinding) :
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

package com.unludev.rickandmorty.ui.homepage

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unludev.rickandmorty.data.model.location.Location
import com.unludev.rickandmorty.databinding.LocationItemBinding

class LocationAdapter(
    private var locations: List<Location>,
    private val onItemClicked: (Location) -> Unit,
) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    companion object { var selectedPosition = RecyclerView.NO_POSITION}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class ViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(location: Location) {
            binding.apply {
                locationName.text = location.name

                if (adapterPosition == selectedPosition) {
                    locationName.setBackgroundColor(Color.LTGRAY)
                } else {
                    locationName.setBackgroundColor(Color.TRANSPARENT)
                }

                locationName.setOnClickListener {
                    onItemClicked(location)
                    selectedPosition = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }
}

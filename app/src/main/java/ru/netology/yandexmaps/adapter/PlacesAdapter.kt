package ru.netology.yandexmaps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.yandexmaps.databinding.CardPlaceSingleBinding
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.holder.PlacesViewHolder

class PlacesAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Place, PlacesViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = CardPlaceSingleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlacesViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }
}
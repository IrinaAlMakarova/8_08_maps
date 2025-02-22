package ru.netology.yandexmaps.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.yandexmaps.dto.Place

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}

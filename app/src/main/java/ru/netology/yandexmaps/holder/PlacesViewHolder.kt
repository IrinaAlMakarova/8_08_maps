package ru.netology.yandexmaps.holder

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.adapter.OnInteractionListener
import ru.netology.yandexmaps.databinding.CardPlaceSingleBinding
import ru.netology.yandexmaps.dto.Place

class PlacesViewHolder(
    private val binding: CardPlaceSingleBinding,
    private val onInteractionListener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
        val apply = binding.apply {
            title.text = place.name


            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.place_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.delete -> {
                                onInteractionListener.onDelete(place)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(place)
                                true
                            }

                            R.id.toPlace -> {
                                onInteractionListener.onToPlace(place)
                                true
                            }

                            else -> false
                        }
                    }
                    show()
                }
            }
        }
    }
}
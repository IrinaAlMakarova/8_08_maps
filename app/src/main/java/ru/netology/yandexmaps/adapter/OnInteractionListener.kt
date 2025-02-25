package ru.netology.yandexmaps.adapter

import ru.netology.yandexmaps.dto.Place

interface OnInteractionListener {
    fun onDelete(place: Place)
    fun onEdit(place: Place)
    fun onToPlace(place: Place)
}
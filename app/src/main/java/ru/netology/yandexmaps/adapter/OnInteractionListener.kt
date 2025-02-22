package ru.netology.yandexmaps.adapter

import ru.netology.yandexmaps.dto.Place

interface OnInteractionListener {
    fun onClick(place: Place)
    fun onDelete(place: Place)
    fun onEdit(place: Place)
}
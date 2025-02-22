package ru.netology.yandexmaps.dto

data class Place(
    val id: Long = 0,
    val name: String,
    val length: Double,
    val width: Double,
)
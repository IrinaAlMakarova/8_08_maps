package ru.netology.yandexmaps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.yandexmaps.dto.Place

@Entity
data class PlaceEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val length: Double,
    val width: Double,
    val name: String,
) {
    companion object {
        fun fromDto(dto: Place): PlaceEntity = with(dto) {
            PlaceEntity(id = id, length = length, width = width, name = name)
        }
    }

    fun toDto(): Place = Place(id = id, length = length, width = width, name = name)
}
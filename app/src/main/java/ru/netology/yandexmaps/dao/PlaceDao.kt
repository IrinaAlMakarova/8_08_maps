package ru.netology.yandexmaps.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.yandexmaps.entity.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM PlaceEntity")
    fun getAll(): Flow<List<PlaceEntity>>

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Query("UPDATE PlaceEntity SET length = :length, width = :width  WHERE id = :id")
    fun updateById(id: Long, length: Double, width: Double)
}
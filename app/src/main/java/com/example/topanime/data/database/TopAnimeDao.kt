package com.example.topanime.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAnimeDao {
    @Query("SELECT * FROM top_anime")
    fun getAllF() : Flow<List<TopAnime>>

    @Query("SELECT * FROM top_anime")
    suspend fun getAll() : List<TopAnime>

    @Query("SELECT * FROM top_anime WHERE id = :id")
    fun findById(id : Long) : Flow<TopAnime>

    @Query("SELECT COUNT(id) FROM top_anime")
    suspend fun topAnimeCount() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopAnimes(topAnimes : List<TopAnime>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopAnime(topAnime : TopAnime)

    @Update
    fun updateTopAnime(topAnime: TopAnime)
}

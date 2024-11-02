package com.example.topanime.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TopMangaDao {
    @Query("SELECT * FROM top_manga")
    fun getAllF() : Flow<List<TopManga>>

    @Query("SELECT * FROM top_manga")
    suspend fun getAll() : List<TopManga>

    @Query("SELECT * FROM top_manga WHERE id = :id")
    fun findById(id : Long) : Flow<TopManga>

    @Query("SELECT COUNT(id) FROM top_manga")
    suspend fun topMangaCount() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopMangas(topMangas : List<TopManga>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopManga(topManga : TopManga)

    @Update
    fun updateTopManga(topManga: TopManga)
}

package com.example.topanime.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonNowDao {
    @Query("SELECT * FROM seasons_now")
    fun getAllF() : Flow<List<SeasonNow>>
    @Query("SELECT * FROM seasons_now")
    suspend fun getAll() : List<SeasonNow>
    @Query("SELECT * FROM seasons_now WHERE id = :id")
    fun findById(id : Long) : Flow<SeasonNow>

    @Query("SELECT COUNT(id) FROM seasons_now")
    suspend fun seasonNowCount() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonsNow(seasonsNow : List<SeasonNow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonNow(seasonNow : SeasonNow)

    @Update
    fun updateSeasonNow(seasonNow: SeasonNow)
}

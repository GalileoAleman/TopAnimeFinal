package com.example.topanime.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SeasonNow::class, TopAnime::class, TopManga::class], version = 1, exportSchema = false)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun seasonNowDao() : SeasonNowDao
    abstract fun topAnimeDao() : TopAnimeDao
    abstract fun topMangaDao() : TopMangaDao
}

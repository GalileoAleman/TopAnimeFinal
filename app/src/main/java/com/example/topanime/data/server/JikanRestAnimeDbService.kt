package com.example.topanime.data.server

import com.example.topanime.data.server.remote.AnimesDb
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanRestAnimeDbService {
    @GET("seasons/now")
    suspend fun getAnimesSeasonNow(@Query("page") page : String = "2") : AnimesDb
    @GET("top/anime")
    suspend fun getTopAnimne(@Query("type") type : String = "tv", @Query("filter") filter : String = "bypopularity", @Query("page") page : String = "1") : AnimesDb
    @GET("top/manga")
    suspend fun getTopManga(@Query("filter") filter : String = "bypopularity", @Query("page") page : String = "1") : AnimesDb
}

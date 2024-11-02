package com.example.topanime.di

import android.app.Application
import androidx.room.Room
import com.example.topanime.data.database.AnimeDataBase
import com.example.topanime.data.server.JikanRestAnimeDbService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestAppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        AnimeDataBase::class.java,
    ).build()

    @Provides
    @Singleton
    fun provideSeasonNowDao(db: AnimeDataBase) = db.seasonNowDao()

    @Provides
    @Singleton
    fun provideTopAnimeDao(db: AnimeDataBase) = db.topAnimeDao()

    @Provides
    @Singleton
    fun provideTopMangaDao(db: AnimeDataBase) = db.topMangaDao()

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl() : String = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Singleton
    @Provides
    fun provideJikanRestAnimeDbService(@ApiUrl apiUrl : String, okHttpClient: OkHttpClient): JikanRestAnimeDbService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(JikanRestAnimeDbService::class.java)
    }
}

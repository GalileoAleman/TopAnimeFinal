package com.example.topanime.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.topanime.data.ImageShaPreDataSource
import com.example.topanime.data.UserShaPreDataSource
import com.example.topanime.data.Validator
import com.example.topanime.data.common.IValidator
import com.example.topanime.data.database.AnimeDataBase
import com.example.topanime.data.database.SeasonNowRoomDataSource
import com.example.topanime.data.database.TopAnimeRoomDataSource
import com.example.topanime.data.database.TopMangaRoomDataSource
import com.example.topanime.data.datasource.*
import com.example.topanime.data.datasource.firebase.FirebaseRemoteDataSource
import com.example.topanime.data.server.JikanRestAnimeDbService
import com.example.topanime.data.server.SeasonNowServerDataSource
import com.example.topanime.data.server.TopAnimeServerDataSource
import com.example.topanime.data.server.TopMangaServerDataSource
import com.example.topanime.data.server.firebase.FirebaseServerDataSource
import com.example.topanime.ui.common.AnimeSharedPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        AnimeDataBase::class.java,
        "anime-db"
    ).build()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): AnimeSharedPreferences {
        return AnimeSharedPreferences(context)
    }

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
    fun provideApiUrl() : String = "https://api.jikan.moe/v4/"

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
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(JikanRestAnimeDbService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule{

    @Binds
    abstract fun bindSeasonNowLocalDataSource(seasonNowRoomDataSource: SeasonNowRoomDataSource) : SeasonNowLocalDataSource

    @Binds
    abstract fun bindSeasonNowRemoteDataSource(seasonNowServerDataSource: SeasonNowServerDataSource) : SeasonNowRemoteDataSource

    @Binds
    abstract fun bindTopAnimeLocalDataSource(topAnimeRoomDataSource: TopAnimeRoomDataSource) : TopAnimeLocalDataSource

    @Binds
    abstract fun bindTopAnimeRemoteDataSource(topAnimeServerDataSource: TopAnimeServerDataSource) : TopAnimeRemoteDataSource

    @Binds
    abstract fun bindTopMangaLocalDataSource(topMangaRoomDataSource: TopMangaRoomDataSource) : TopMangaLocalDataSource

    @Binds
    abstract fun bindTopMangaRemoteDataSource(topMangaServerDataSource: TopMangaServerDataSource) : TopMangaRemoteDataSource

    @Binds
    abstract fun bindFirebaseRemoteDataSource(firebaseServerDataSource: FirebaseServerDataSource) : FirebaseRemoteDataSource

    @Binds
    abstract fun bindValidator(validator: Validator) : IValidator

    @Binds
    abstract fun bindImageLocalDataSource(imageShaPreDataSource: ImageShaPreDataSource) : ImageLocalDataSource

    @Binds
    abstract fun bindUserLocalDataSource(userShaPreDataSource: UserShaPreDataSource) : UserLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
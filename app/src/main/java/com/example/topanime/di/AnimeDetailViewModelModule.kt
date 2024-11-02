package com.example.topanime.di

import androidx.lifecycle.SavedStateHandle
import com.example.topanime.ui.detail.AnimeDetailFragmentArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AnimeDetailViewModelModule {

    @Provides
    @ViewModelScoped
    @AnimeId
    fun provideAnimeId(savedStateHandle: SavedStateHandle) =
        AnimeDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).id

    @Provides
    @ViewModelScoped
    @AnimeCat
    fun provideAnimeCat(savedStateHandle: SavedStateHandle) =
        AnimeDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).category
}
package com.example.rickandmortycharacterlistapp.core.hilt

import com.example.rickandmortycharacterlistapp.data.character.CharacterApiSource
import com.example.rickandmortycharacterlistapp.data.character.CharacterApiSourceImpl
import com.example.rickandmortycharacterlistapp.data.character.mapper.CharacterMapper
import com.example.rickandmortycharacterlistapp.data.pagination.mapper.PaginationMapper
import com.example.rickandmortycharacterlistapp.framework.character.api.CharacterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiSourceModule {

    @Provides
    @Singleton
    fun provideCharacterApiSource(
        paginationMapper: PaginationMapper,
        characterMapper: CharacterMapper,
        apiService: CharacterApiService
    ): CharacterApiSource =
        CharacterApiSourceImpl(paginationMapper, characterMapper, apiService)
}
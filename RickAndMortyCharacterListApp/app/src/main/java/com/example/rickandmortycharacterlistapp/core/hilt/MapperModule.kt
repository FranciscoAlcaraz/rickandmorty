package com.example.rickandmortycharacterlistapp.core.hilt

import com.example.rickandmortycharacterlistapp.data.character.mapper.CharacterMapper
import com.example.rickandmortycharacterlistapp.data.pagination.mapper.PaginationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providePaginationMapper(): PaginationMapper = PaginationMapper()

    @Provides
    fun provideCharacterMapper(): CharacterMapper = CharacterMapper()

}
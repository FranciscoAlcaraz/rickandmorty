package com.example.rickandmortycharacterlistapp.core.hilt

import com.example.rickandmortycharacterlistapp.data.character.CharacterApiSource
import com.example.rickandmortycharacterlistapp.data.character.CharacterRepoImpl
import com.example.rickandmortycharacterlistapp.domain.repository.CharacterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideCharacterRepo(characterApiSource: CharacterApiSource): CharacterRepo =
        CharacterRepoImpl(characterApiSource)
}
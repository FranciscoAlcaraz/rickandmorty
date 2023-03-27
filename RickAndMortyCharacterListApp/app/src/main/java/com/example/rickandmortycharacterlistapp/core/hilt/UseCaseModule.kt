package com.example.rickandmortycharacterlistapp.core.hilt

import com.example.rickandmortycharacterlistapp.domain.repository.CharacterRepo
import com.example.rickandmortycharacterlistapp.domain.usecase.character.GetCharactersUseCase
import com.example.rickandmortycharacterlistapp.domain.usecase.character.imp.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCharactersUseCase(characterRepo: CharacterRepo): GetCharactersUseCase =
        GetCharactersUseCaseImpl(characterRepo)
}
package com.example.rickandmortycharacterlistapp.core.hilt

import com.example.rickandmortycharacterlistapp.core.api.client.ApiClient
import com.example.rickandmortycharacterlistapp.framework.character.api.CharacterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideCharacterApiService(): CharacterApiService =
        ApiClient.getClient().create(CharacterApiService::class.java)
}
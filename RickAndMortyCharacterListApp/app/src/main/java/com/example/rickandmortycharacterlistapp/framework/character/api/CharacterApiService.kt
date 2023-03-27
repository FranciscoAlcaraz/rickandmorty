package com.example.rickandmortycharacterlistapp.framework.character.api

import com.example.rickandmortycharacterlistapp.data.character.api.entity.CharacterApiEntity
import com.example.rickandmortycharacterlistapp.data.pagination.PaginationApiEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CharacterApiService {

    @Headers("Cache-Control: max-age=86400")
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null,
    ): PaginationApiEntity<CharacterApiEntity>
}
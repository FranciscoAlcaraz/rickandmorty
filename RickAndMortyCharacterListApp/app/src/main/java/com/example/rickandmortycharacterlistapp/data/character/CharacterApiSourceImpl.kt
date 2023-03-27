package com.example.rickandmortycharacterlistapp.data.character

import com.example.rickandmortycharacterlistapp.data.character.mapper.CharacterMapper
import com.example.rickandmortycharacterlistapp.data.pagination.mapper.PaginationMapper
import com.example.rickandmortycharacterlistapp.domain.Result
import com.example.rickandmortycharacterlistapp.framework.character.api.CharacterApiService
import javax.inject.Inject

class CharacterApiSourceImpl @Inject constructor(
    private val paginationMapper: PaginationMapper,
    private val characterMapper: CharacterMapper,
    private val apiService: CharacterApiService
) : CharacterApiSource {

    override suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ) = try {
        Result.Success(
            paginationMapper.toDomain(
                apiService.getCharacters(page, name, status, species, type, gender),
                characterMapper::toDomain
            )
        )
    } catch (e: Exception) {
        Result.Fail(e.message.toString())
    }
}
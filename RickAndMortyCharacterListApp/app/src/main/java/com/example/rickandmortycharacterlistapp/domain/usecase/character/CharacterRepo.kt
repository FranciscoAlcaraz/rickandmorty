package com.example.rickandmortycharacterlistapp.domain.usecase.character

import com.example.rickandmortycharacterlistapp.domain.Result
import com.example.rickandmortycharacterlistapp.domain.model.Character
import com.example.rickandmortycharacterlistapp.domain.model.Pagination

interface GetCharactersUseCase {
    suspend fun invoke(
        page: Int = 1, name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Result<Pagination<Character>, String>
}
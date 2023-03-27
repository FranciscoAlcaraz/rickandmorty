package com.example.rickandmortycharacterlistapp.domain.repository

import com.example.rickandmortycharacterlistapp.domain.Result
import com.example.rickandmortycharacterlistapp.domain.model.Character
import com.example.rickandmortycharacterlistapp.domain.model.Pagination

interface CharacterRepo {
    suspend fun getCharacters(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Result<Pagination<Character>, String>
}
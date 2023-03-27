package com.example.rickandmortycharacterlistapp.data.character

import com.example.rickandmortycharacterlistapp.domain.repository.CharacterRepo
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(private val characterApiSource: CharacterApiSource) :
    CharacterRepo {

    override suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ) = characterApiSource.getCharacters(page, name, status, species, type, gender)
}
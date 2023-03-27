package com.example.rickandmortycharacterlistapp.domain.usecase.character.imp

import com.example.rickandmortycharacterlistapp.domain.repository.CharacterRepo
import com.example.rickandmortycharacterlistapp.domain.usecase.character.GetCharactersUseCase
import javax.inject.Inject

class GetCharactersUseCaseImpl @Inject constructor(private val repoImpl: CharacterRepo) :
    GetCharactersUseCase {
    override suspend fun invoke(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ) = repoImpl.getCharacters(page, name, status, species, type, gender)
}
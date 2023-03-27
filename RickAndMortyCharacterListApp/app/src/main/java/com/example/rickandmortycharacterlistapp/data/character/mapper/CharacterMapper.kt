package com.example.rickandmortycharacterlistapp.data.character.mapper

import com.example.rickandmortycharacterlistapp.data.character.api.entity.CharacterApiEntity
import com.example.rickandmortycharacterlistapp.domain.model.Character

class CharacterMapper {

    fun toDomain(characterApiEntity: CharacterApiEntity) =
        Character(
            id = characterApiEntity.id,
            name = characterApiEntity.name,
            image = characterApiEntity.image,
            status = characterApiEntity.status,
            species = characterApiEntity.species,
            type = characterApiEntity.type,
            gender = characterApiEntity.gender
        )
}
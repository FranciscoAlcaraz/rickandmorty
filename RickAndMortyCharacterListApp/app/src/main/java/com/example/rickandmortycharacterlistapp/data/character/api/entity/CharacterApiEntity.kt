package com.example.rickandmortycharacterlistapp.data.character.api.entity

import com.google.gson.annotations.SerializedName

data class CharacterApiEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
)
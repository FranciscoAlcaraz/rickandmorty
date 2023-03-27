package com.example.rickandmortycharacterlistapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Long,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
) : Parcelable
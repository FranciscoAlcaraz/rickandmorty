package com.example.rickandmortycharacterlistapp.data.pagination

import com.google.gson.annotations.SerializedName

data class PaginationApiEntity<T>(
    @SerializedName("info") val info: PaginationInfoApiEntity,
    @SerializedName("results") val results: List<T>
)
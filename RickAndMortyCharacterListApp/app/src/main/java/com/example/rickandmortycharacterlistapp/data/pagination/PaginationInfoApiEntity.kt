package com.example.rickandmortycharacterlistapp.data.pagination

import com.google.gson.annotations.SerializedName

data class PaginationInfoApiEntity(@SerializedName("next") val next: String? = null)
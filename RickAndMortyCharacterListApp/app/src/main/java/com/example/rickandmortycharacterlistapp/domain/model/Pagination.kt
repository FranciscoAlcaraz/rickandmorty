package com.example.rickandmortycharacterlistapp.domain.model

data class Pagination<T>(val items: List<T>, val hasMoreItems: Boolean)
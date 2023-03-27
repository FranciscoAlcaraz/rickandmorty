package com.example.rickandmortycharacterlistapp.domain

sealed class Result<out T, out Y> {
    data class Success<T>(val data: T) : Result<T, Nothing>()
    data class Fail<Y>(val error: Y) : Result<Nothing, Y>()
}
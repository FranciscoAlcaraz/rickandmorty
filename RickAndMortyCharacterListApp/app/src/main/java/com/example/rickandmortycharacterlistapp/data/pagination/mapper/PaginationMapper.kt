package com.example.rickandmortycharacterlistapp.data.pagination.mapper

import com.example.rickandmortycharacterlistapp.data.pagination.PaginationApiEntity
import com.example.rickandmortycharacterlistapp.domain.model.Pagination

class PaginationMapper {

    fun <T, R> toDomain(pagination: PaginationApiEntity<T>, itemMap: (T) -> R): Pagination<R> =
        Pagination(pagination.results.map(itemMap), pagination.info.next != null)
}

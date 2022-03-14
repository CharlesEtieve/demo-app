package com.demo.app.domain.models.errors

typealias DomainDataError = Map<String, String>?

data class DomainError(
    val errorKey: String,
    val data: DomainDataError = null
)
